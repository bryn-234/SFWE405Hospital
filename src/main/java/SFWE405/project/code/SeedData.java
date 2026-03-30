package SFWE405.project.code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import SFWE405.project.code.Entities.Profile;
import SFWE405.project.code.Repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (profileRepository.count() == 0) {

            Profile p = new Profile();
            p.setId(1L);
            p.setUsername("joseph");
            p.setEmail("joseph@example.com");
            p.setPassword(passwordEncoder.encode("password"));
            p.setRole("PATIENT");

            profileRepository.save(p);

            System.out.println("Seeded default user: joseph / password");
        }
    }
}
