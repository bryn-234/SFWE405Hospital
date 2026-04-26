package SFWE405.project.code.Controllers.patient;

import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import SFWE405.project.code.Services.AuthService;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private AuthService authService;

    @GetMapping("/home")
    public String patientHome(Model model) {

        Profile profile = authService.getLoggedInProfile();
        Patient patient = profile.getPatient();

        model.addAttribute("profile", profile);
        model.addAttribute("patient", patient);

        return "patient/home";
    }
}
