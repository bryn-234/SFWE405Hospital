package SFWE405.project.code.Controllers.doctor;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import SFWE405.project.code.Repositories.*;
import SFWE405.project.code.Services.*;
import SFWE405.project.code.Entities.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private ProfileRepository profileRepo;
    
    @Autowired
    private AuthService authService;

    @GetMapping("/home")
    public String doctorHome(Model model) {

        Profile profile = authService.getLoggedInProfile();
        Doctor doctor = profile.getDoctor();
        Schedule schedule = doctor.getSchedule();
        Department department = doctor.getDepartment();
        model.addAttribute("doctor", doctor);
        model.addAttribute("profile", profile);
        model.addAttribute("schedule", schedule);
        model.addAttribute("department", department);
        model.addAttribute("timeSlots", schedule.getTimeSlot());
        return "doctor/home";
    }


}
