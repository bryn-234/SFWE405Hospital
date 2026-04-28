package SFWE405.project.code.Services;

import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SFWE405.project.code.Repositories.ProfileRepository;
import SFWE405.project.code.Entities.Profile;

/**
 * 
 *  Centralizes authorization logic for profile‑related actions, making ownership 
 *  and role checks reusable across the application.
 * 
 *  Methods provide easier access to authentication, getting existing profiles
 *  and checking for authorization of an account.
 * 
 *  @author Joseph Corella
 */
@Service
public class AuthService {
    
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Constructor
    public AuthService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * 
     *  Retrieves the currently authenticated user from Spring Security and 
     *  returns the corresponding Profile.
     * 
     *  @return Profile Entity (if profile exists)
     * 
     *  @author Joseph Corella
     */
    public Profile getLoggedInProfile() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return profileRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in profile not found"));
    }

    public boolean isOwner(Profile target) {
        return target.getUsername().equals(getLoggedInProfile().getUsername());
    }
    
    public boolean isDoctor() {
        return getLoggedInProfile().getDoctor() != null;
    }

    public boolean isPatient() {
        return getLoggedInProfile().getPatient() != null;
    }

    @Transactional
    public void signupPatient(Profile profile, String firstName, String lastName) {

        // if both username and email exist, throw error
        if (profileRepository.findByEmail(profile.getEmail()).isPresent() && profileRepository.findByUsername(profile.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username and Email already in use");
        } else if (profileRepository.findByEmail(profile.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        } else if (profileRepository.findByUsername(profile.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patientRepository.save(patient);

        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        profile.setRole("PATIENT");
        profile.setPatient(patient);

        profileRepository.save(profile);
    }
}
