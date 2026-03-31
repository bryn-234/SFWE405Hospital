package SFWE405.project.code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import SFWE405.project.code.Entities.*;
import SFWE405.project.code.Repositories.*;

import java.time.LocalDateTime;



@Component
public class SeedData implements CommandLineRunner {

    @Autowired private ProfileRepository profileRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private HospitalRepository hospitalRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private TimeSlotRepository timeSlotRepository;
    
    // Injections for the medical entities
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private PatientRepository patientRepository;

    @Override
    //changed the seeding so now when server start a hospital, dept, patient and doctor is created for easier testing
    public void run(String... args) {
        
        if (profileRepository.count() == 0) {
            
            //hospital seed
            Hospital h = new Hospital();
            h.setId(1L);
            h.setName("General Hospital");
            h.setCapacity(100);
            h.setOccupancy(0);
            hospitalRepository.save(h);

            //department seed
            Department dept = new Department();
            dept.setId(1L);
            dept.setName("Department");
            dept.setHospital(h);
            departmentRepository.save(dept);

            // doctor entity
            Doctor dEnt = new Doctor();
            dEnt.setName("Doctor 1");
            dEnt.setDepartment(dept);
            doctorRepository.save(dEnt);

            //doctor profile
            Profile dProf = new Profile();
            dProf.setUsername("doctor");
            dProf.setEmail("doctor@example.com");
            dProf.setPassword(passwordEncoder.encode("password"));
            dProf.setRole("DOCTOR");
            dProf.setDoctor(dEnt);
            profileRepository.save(dProf);
            
            //patient entity
            Patient pEnt = new Patient();
            pEnt.setFirstName("Joseph");
            patientRepository.save(pEnt);

            // patient profile
            Profile pProf = new Profile();
            pProf.setUsername("joseph");
            pProf.setEmail("joseph@example.com");
            pProf.setPassword(passwordEncoder.encode("password"));
            pProf.setRole("PATIENT");
            pProf.setPatient(pEnt); // Link to Patient Entity
            profileRepository.save(pProf);

            // seed a time slot
            TimeSlot ts = new TimeSlot();
            ts.setStartTime(LocalDateTime.now().plusDays(1));
            ts.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
            ts.setAvailable(true);
            timeSlotRepository.save(ts);

            System.out.println("Database Seeded Successfully:");
            System.out.println("  - Hospital (ID: 1)");
            System.out.println("  - Doctor: 'doctor' (ID: 1)");
            System.out.println("  - Patient: 'joseph' (ID: 1)");
            System.out.println("  - TimeSlot (ID: 1)");
            System.out.println ("All passwords are password");
        }
    }
}

