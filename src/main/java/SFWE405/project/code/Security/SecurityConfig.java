package SFWE405.project.code.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

/**
 * 
 * Configures Spring Security for our application. 
 * 
 * This class defines authentication and authorization rules. It integrates our
 * ProfileDetailsService so that user login is backed by profile entities in the database.
 * 
 * This class also defines the endpoints for login and logout. The password encoder is also
 * used to verify user credentials.
 *  
 * @author Joseph Corella
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ProfileDetailsService profileDetailsService;

    // Constructor injection of ProfileDetailsService
    public SecurityConfig(ProfileDetailsService profileDetailsService) {
        this.profileDetailsService = profileDetailsService;
    }

    // Configure HTTP security, authentication, and authorization rules
    // Links /login, /register for login, and /logout for logout
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/slots").hasAnyAuthority("DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/slots/**").hasAnyAuthority("DOCTOR")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) 
                .userDetailsService(profileDetailsService)
                .formLogin(form -> form
                .defaultSuccessUrl("/home", true)
                )
                .userDetailsService(profileDetailsService)
                .formLogin(form -> form
                    .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
