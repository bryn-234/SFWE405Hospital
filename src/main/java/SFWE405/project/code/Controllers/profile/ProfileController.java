package SFWE405.project.code.Controllers.profile;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import SFWE405.project.code.Services.ProfileService;
import SFWE405.project.code.Security.ProfileDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import SFWE405.project.code.DTOs.ProfileUpdateRequest;
import SFWE405.project.code.Entities.Profile;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired private ProfileService profileService;
    @Autowired private ProfileDetailsService profileDetailsService;
    
    @GetMapping("/edit")
    public String editProfile(Model model, Authentication auth) {
        Profile profile = profileService.getProfileByUsername(auth.getName());
        model.addAttribute("profile", profile);

        String role = auth.getAuthorities().iterator().next().getAuthority();
        String homeUrl = role.equals("DOCTOR") ? "/doctor/home" : "/patient/home";
        model.addAttribute("homeUrl", homeUrl);

        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @ModelAttribute ProfileUpdateRequest request,
            org.springframework.security.core.Authentication auth,
            HttpServletRequest httpRequest,
            Model model) {

        Profile profile = profileService.getProfileByUsername(auth.getName());
        profileService.updateProfile(profile.getId(), request);

        // Force re-authentication with updated credentials
        httpRequest.getSession().invalidate();
        httpRequest.getSession(true);

        String newUsername = request.getUsername() != null ? request.getUsername() : auth.getName();
        UserDetails updatedDetails = profileDetailsService.loadUserByUsername(newUsername);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                updatedDetails, null, updatedDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        httpRequest.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return "redirect:/profile/edit?success";
    }
}
