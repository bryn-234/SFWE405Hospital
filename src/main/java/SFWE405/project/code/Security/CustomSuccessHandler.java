package SFWE405.project.code.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Determine role
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("DOCTOR")) {
            response.sendRedirect("/doctor/home");
        } else if (role.equals("PATIENT")) {
            response.sendRedirect("/patient/home");
        } else {
            SecurityContextHolder.clearContext();
            response.sendRedirect("/login?error=role");
        }
    }
}