package SFWE405.project.code.Controllers.profile;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import SFWE405.project.code.Services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import SFWE405.project.code.DTOs.ProfileUpdateRequest;
import SFWE405.project.code.Entities.Profile;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired private ProfileService profileService;
    
    @GetMapping("/edit")
    public String editProfile(Model model, Authentication auth) {

        Profile profile = profileService.getProfileByUsername(auth.getName());
        model.addAttribute("profile", profile);

        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
        @ModelAttribute ProfileUpdateRequest request,
        org.springframework.security.core.Authentication auth,
        Model model) {

        Profile profile = profileService.getProfileByUsername(auth.getName());
        profileService.updateProfile(profile.getId(), request);
        model.addAttribute("profile", profileService.getProfileByUsername(auth.getName()));
        return "redirect:/profile/edit?success";
    }
}
