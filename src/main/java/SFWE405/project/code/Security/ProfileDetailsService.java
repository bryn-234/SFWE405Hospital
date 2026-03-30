package SFWE405.project.code.Security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SFWE405.project.code.Repositories.ProfileRepository;
import SFWE405.project.code.Entities.Profile;

/**
 *  Loads Profile Entities for Spring Security Authentication
 * 
 *  This connects our profile table to spring secruity by implementing UserDetailService.
 *  When a log in attempt is made, Spring Security calls loadUserByUsername(), and we return
 *  a built Spring Security User from profile.
 * 
 *  We essentially adapt our Profile entity into Spring Security's UserDetails via User Builder.
 * 
 * @author Joseph Corella
 */

@Service
public class ProfileDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    //Constructor
    public ProfileDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    /**
     * Loads a user by username for Spring Security authentication.
     * 
     * @param username the username to load
     * @return UserDetails object built from Profile entity
     * 
     * @author Joseph Corella
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Find the profile by username
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Assign a role to the profile
        String role = profile.getDoctor() != null ? "DOCTOR" :
                      profile.getPatient() != null ? "PATIENT" :
                      "USER";

        // Build the profile with correct attributes
        return User.withUsername(profile.getUsername())
                .password(profile.getPassword())
                .roles(role)
                .build();
    }
}
