package SFWE405.project.code.Controllers.doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import SFWE405.project.code.Repositories.*;
import SFWE405.project.code.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import SFWE405.project.code.DTOs.HospitalOccupancyDTO;
import SFWE405.project.code.Entities.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private TimeSlotRepository tsRepo;
    
    @Autowired
    private AuthService authService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/home")
    public String doctorHome(Model model) {

        Profile profile = authService.getLoggedInProfile();
        Doctor doctor = profile.getDoctor();
        Schedule schedule = doctor.getSchedule();
        Department department = doctor.getDepartment();
        Hospital hospital = doctor.getDepartment().getHospital();
        Long hospitalId = hospital.getId();
        HospitalOccupancyDTO occupancy = hospitalService.getHospitalOccupancy(hospitalId);

        List<TimeSlot> sortedSlots = schedule.getTimeSlot().stream()
        .sorted(Comparator.comparing(TimeSlot::getDate)
                .thenComparing(TimeSlot::getStartTime))
        .collect(Collectors.toList(
        ));
        
        model.addAttribute("doctor", doctor);
        model.addAttribute("profile", profile);
        model.addAttribute("schedule", schedule);
        model.addAttribute("department", department);
        model.addAttribute("timeSlots", sortedSlots);
        model.addAttribute("occupancy", occupancy);
        return "doctor/home";
    }

    @GetMapping("/editSchedule")
    public String editSchedule(Model model){
        Profile profile = authService.getLoggedInProfile();
        Doctor doctor = profile.getDoctor();
        Schedule schedule = doctor.getSchedule();
        model.addAttribute("doctor", doctor);
        model.addAttribute("timeSlots", schedule.getTimeSlot());
        return "doctor/editSchedule";
    }

    @PostMapping("/editAvailability")
    public String editAvailability(HttpServletRequest httpRequest, Model model){
        Profile profile = authService.getLoggedInProfile();
        Doctor doctor = profile.getDoctor();
        Schedule schedule = doctor.getSchedule();

        for(TimeSlot ts: schedule.getTimeSlot()){
            String param = httpRequest.getParameter("available_" + ts.getId());
            ts.setAvailable(param != null);
            tsRepo.save(ts);
        }

        return "redirect:/doctor/editSchedule";
    }


    @PostMapping("/addSlot")
    public String addSlot(
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam Boolean available,
            RedirectAttributes redirectAttributes) {

        Profile profile = authService.getLoggedInProfile();
        Doctor doctor = profile.getDoctor();

        TimeSlot slot = new TimeSlot();
        slot.setDate(LocalDate.parse(date));
        slot.setStartTime(LocalTime.parse(startTime));
        slot.setEndTime(LocalTime.parse(endTime));
        slot.setAvailable(available);
        slot.setSchedule(doctor.getSchedule());

        appointmentService.addAvailableSlot(slot);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/doctor/editSchedule";
    }

    @PostMapping("/deleteSlot/{id}")
    public String deleteSlot(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.removeAvailableSlot(id);
            redirectAttributes.addFlashAttribute("success", true);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/doctor/editSchedule";
    }

}
