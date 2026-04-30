package SFWE405.project.code.Controllers.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("doctor", doctor);
        model.addAttribute("profile", profile);
        model.addAttribute("schedule", schedule);
        model.addAttribute("department", department);
        model.addAttribute("timeSlots", schedule.getTimeSlot());
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

}