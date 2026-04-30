package SFWE405.project.code.Controllers.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import SFWE405.project.code.Services.*;
import SFWE405.project.code.Entities.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/home")
    public String doctorHome(Model model) {
        Profile profile = authService.getLoggedInProfile();
        Doctor doctor = profile.getDoctor();
        
        model.addAttribute("doctor", doctor);
        model.addAttribute("profile", profile);
        model.addAttribute("department", doctor.getDepartment());
        model.addAttribute("timeSlots", scheduleService.getSortedSlots(doctor.getSchedule()));
        model.addAttribute("occupancy", hospitalService.getHospitalOccupancy(doctor.getDepartment().getHospital().getId()));
        
        return "doctor/home";
    }

    @GetMapping("/editSchedule")
    public String editSchedule(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model) {

        LocalDate now = LocalDate.now();
        int targetMonth = (month != null) ? month : now.getMonthValue();
        int targetYear = (year != null) ? year : now.getYear();
        LocalDate currentView = LocalDate.of(targetYear, targetMonth, 1);

        Doctor doctor = authService.getLoggedInProfile().getDoctor();

        // Populate Model with Service Data
        model.addAttribute("doctor", doctor);
        model.addAttribute("timeSlots", scheduleService.getSlotsForMonth(doctor.getSchedule(), targetYear, targetMonth));
        model.addAttribute("daysInMonth", currentView.lengthOfMonth());
        model.addAttribute("offset", currentView.getDayOfWeek().getValue() % 7);
        model.addAttribute("currentMonthName", currentView.getMonth().toString());
        model.addAttribute("currentYear", targetYear);
        model.addAttribute("targetMonth", targetMonth);
        
        // Nav Logic
        model.addAttribute("prevMonth", currentView.minusMonths(1).getMonthValue());
        model.addAttribute("prevYear", currentView.minusMonths(1).getYear());
        model.addAttribute("nextMonth", currentView.plusMonths(1).getMonthValue());
        model.addAttribute("nextYear", currentView.plusMonths(1).getYear());

        return "doctor/editSchedule";
    }

    @PostMapping("/editAvailability")
    public String addNewSlot(
            @RequestParam String day, 
            @RequestParam String startTime, 
            @RequestParam String endTime,
            @RequestParam Integer currentMonth,
            @RequestParam Integer currentYear,
            RedirectAttributes redirectAttributes) {
        
        try {
            Schedule schedule = authService.getLoggedInProfile().getDoctor().getSchedule();
            scheduleService.createAndSaveSlot(schedule, day, startTime, endTime);
            redirectAttributes.addFlashAttribute("success", "Slot created successfully!");
        } catch (Exception e) {
            // Pass the error message back to the UI
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        redirectAttributes.addAttribute("month", currentMonth);
        redirectAttributes.addAttribute("year", currentYear);
        return "redirect:/doctor/editSchedule";
    }

    @PostMapping("/toggleSlot")
    public String toggleSlot(
            @RequestParam("slotId") Long slotId,
            @RequestParam Integer currentMonth,
            @RequestParam Integer currentYear,
            RedirectAttributes redirectAttributes) {
        
        scheduleService.toggleSlotAvailability(slotId);
        
        // Ensure the page reloads to the same month/year
        redirectAttributes.addAttribute("month", currentMonth);
        redirectAttributes.addAttribute("year", currentYear);
        
        return "redirect:/doctor/editSchedule";
    }
}