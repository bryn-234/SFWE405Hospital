package SFWE405.project.code.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling authentication-related requests, such as login. Redirects users to login page.
 * 
 * @author Joseph Corella
 */

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // resolves login.html
    }
}
