package SFWE405.project.code.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import SFWE405.project.code.DTOs.ProfileUpdateRequest;
import SFWE405.project.code.Entities.Profile;
import SFWE405.project.code.Repositories.ProfileRepository;

/**
 * @author Joseph Corella
 * 
 *  Service helps with profile editing permissions.
 *  Security and Login are controlled by the built-in
 *  Spring Boot Security
 */

@Service
public class ProfileService {
    
    private ProfileRepository profileRepository;
    private AuthService authService;
    private PasswordEncoder passwordEncoder;

    // Constructor
    public ProfileService(ProfileRepository profileRepository, AuthService authService, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Updates a user's profile information. 
     * 
     * The method is assumed to be called after authentication, and it
     * checks if the logged-in user is the owner of the profile before allowing updates.
     * 
     * The ProfileUpdateRequest DTO contains the fields that can be updated (password, email, username).
     * This encapsulates the variables that can be updated, so that we don't accidentally allow updates to 
     * fields that should not be changed by the user.
     * 
     * @param id
     * @param request
     * @return
     * 
     * @author Joseph Corella
     */
    @Transactional
    public Profile updateProfile(Long id, ProfileUpdateRequest request) {
       
        Profile target = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Auhtorization Check: Only the owner of the profile can update it
        if (!authService.isOwner(target)) {
            throw new RuntimeException("Unauthorized to update this profile");
        }

        if (request.getPassword() != null) {
            target.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getEmail() != null) {
            target.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            target.setUsername(request.getUsername());
        }

        return target;
    }
}
