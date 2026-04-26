package SFWE405.project.code.Controllers;

import SFWE405.project.code.Entities.Profile;
import SFWE405.project.code.Repositories.ProfileRepository;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Repositories.PatientRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;


/**
 * Controller for handling authentication-related requests, such as login. Redirects users to login page.
 * 
 * @author Joseph Corella
 */

@Controller
public class AuthController {
    @Autowired
    private ProfileRepository profileRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/login")
    public String login() {
        return "login"; // resolves login.html
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute Profile profile,
                         @RequestParam String firstName,
                         @RequestParam String lastName) {

        profile.setPassword(passwordEncoder.encode(profile.getPassword()));

        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patientRepo.save(patient);

        profile.setRole("PATIENT");
        profile.setPatient(patient);

        profileRepo.save(profile);

        return "redirect:/login";
    }
}
