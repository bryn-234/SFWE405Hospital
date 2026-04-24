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
     * Retrieves a user's profile by their username. 
     * This is used to display profile information on the frontend.
     * 
     * @param username
     * @return
     */
    public Profile getProfileByUsername(String username) {
        return profileRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
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

        //Email uniqueness check
        if(profileRepository.findByEmail(request.getEmail()).isPresent() && !request.getEmail().equals(target.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        //Username uniqueness check
        if(profileRepository.findByUsername(request.getUsername()).isPresent() && !request.getUsername().equals(target.getUsername())) {
            throw new RuntimeException("Username already in use");
        }

        // Authorization Check: Only the owner of the profile can update it
        if (!authService.isOwner(target)) {
            throw new RuntimeException("Unauthorized to update this profile");
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            target.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            target.setEmail(request.getEmail());
        }
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            target.setUsername(request.getUsername());
        }
        return target;
    }
}
