package SFWE405.project.code.Controllers;

import SFWE405.project.code.Entities.Profile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import SFWE405.project.code.Services.AuthService;

/**
 * Controller for handling authentication-related requests, such as login. Redirects users to login page.
 * 
 * @author Joseph Corella
 */

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

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
                         @RequestParam String lastName,
                         Model model) {
        try {
            authService.signupPatient(profile, firstName, lastName);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
}