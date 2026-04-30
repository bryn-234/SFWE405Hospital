package SFWE405.project.code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import SFWE405.project.code.Entities.*;
import SFWE405.project.code.Repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;



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
    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private AppointmentRepository appointmentRepository;

    @Override
    //changed the seeding so now when server start a hospital, dept, patient and doctor is created for easier testing
    public void run(String... args) {
        
        if (profileRepository.count() == 0) {
            
            //hospital seed
            Hospital h = new Hospital();
            h.setId(1L);
            h.setName("UofA Hospital");
            h.setCapacity(100);
            h.setOccupancy(0);
            hospitalRepository.save(h);

            //department seeds
            Department dept1 = new Department();
            dept1.setId(1L);
            dept1.setName("Cardiology Department");
            dept1.setHospital(h);
            departmentRepository.save(dept1);

            Department dept2 = new Department();
            dept2.setId(2L);
            dept2.setName("Surgery Department");
            dept2.setHospital(h);
            departmentRepository.save(dept2);

            // doctor entity
            Doctor dEnt = new Doctor();
            dEnt.setName("Cerny");
            dEnt.setDepartment(dept1);
            doctorRepository.save(dEnt);

            dept1.addDoctor(dEnt);
            departmentRepository.save(dept1);

            //doctor profile
            Profile dProf = new Profile();
            dProf.setUsername("doctor");
            dProf.setEmail("drCerny@example.com");
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

            //seed doctor schedule
            Schedule schedule = new Schedule();
            schedule.setDoctor(dEnt);
            scheduleRepository.save(schedule);

            dEnt.setSchedule(schedule);
            doctorRepository.save(dEnt);

            // seed time slots
            TimeSlot ts1 = new TimeSlot();
            ts1.setDate(LocalDate.of(2026, 05, 02));
            ts1.setStartTime(LocalTime.of(1, 00));
            ts1.setEndTime(LocalTime.of(3, 00));
            ts1.setAvailable(true);
            ts1.setSchedule(schedule);
            timeSlotRepository.save(ts1);

            TimeSlot ts2 = new TimeSlot();
            ts2.setDate(LocalDate.of(2026,05,06));
            ts2.setStartTime(LocalTime.of(1, 45));
            ts2.setEndTime(LocalTime.of(2, 30));
            ts2.setAvailable(false);
            ts2.setSchedule(schedule);
            timeSlotRepository.save(ts2);

            TimeSlot ts3 = new TimeSlot();
            ts3.setDate(LocalDate.of(2026,05,12));
            ts3.setStartTime(LocalTime.of(9, 00));
            ts3.setEndTime(LocalTime.of(10, 00));
            ts3.setAvailable(true);
            ts3.setSchedule(schedule);
            timeSlotRepository.save(ts3);

            TimeSlot ts4 = new TimeSlot();
            ts4.setDate(LocalDate.of(2026,04,30));
            ts4.setStartTime(LocalTime.of(3, 00));
            ts4.setEndTime(LocalTime.of(4, 30));
            ts4.setAvailable(true);
            ts4.setSchedule(schedule);
            timeSlotRepository.save(ts4);

            TimeSlot ts5 = new TimeSlot();
            ts5.setDate(LocalDate.of(2026,05,06));
            ts5.setStartTime(LocalTime.of(11, 30));
            ts5.setEndTime(LocalTime.of(12, 30));
            ts5.setAvailable(true);
            ts5.setSchedule(schedule);
            timeSlotRepository.save(ts5);

            //seed appointment
            Appointment app = new Appointment();
            app.setAppointmentDate(LocalDate.of(2026,05,06));
            app.setReasonForVisit("Chest Pain");
            app.setStatus("CONFIRMED");
            app.setCost(150);
            app.setRoomNum(405);
            app.setTimeslot(ts2);
            app.setPatient(pEnt);
            appointmentRepository.save(app);

            ts2.setAppointment(app);
            timeSlotRepository.save(ts2);

            System.out.println("Database Seeded Successfully:");
            System.out.println("  - Hospital (ID: 1)");
            System.out.println("  - Doctor: 'doctor' (ID: 1)");
            System.out.println("  - Patient: 'joseph' (ID: 1)");
            System.out.println("  - Schedule (ID: 1)");
            System.out.println("  - TimeSlot (ID: 1)");
            System.out.println ("All passwords are password");
        }
    }
}
