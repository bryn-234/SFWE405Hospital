package SFWE405.project.code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import SFWE405.project.code.Entities.*;
import SFWE405.project.code.Repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    // Injections for the medical entities
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void run(String... args) {

        if (hospitalRepository.count() == 0 &&
                departmentRepository.count() == 0 &&
                doctorRepository.count() == 0 &&
                patientRepository.count() == 0) {

            // -------------------------
            // Hospital
            // -------------------------
            Hospital hospital = new Hospital();
            hospital.setName("UofA Hospital");
            hospital.setCapacity(100);
            hospital.setOccupancy(0);
            hospitalRepository.save(hospital);

            // -------------------------
            // Departments
            // -------------------------
            String[] departmentNames = {
                    "Emergency Department",
                    "Radiology",
                    "Pediatrics",
                    "Cardiology",
                    "Oncology"
            };

            Department[] departments = new Department[departmentNames.length];

            for (int i = 0; i < departmentNames.length; i++) {
                Department department = new Department();
                department.setName(departmentNames[i]);
                department.setHospital(hospital);
                departmentRepository.save(department);
                departments[i] = department;
            }

            // -------------------------
            // Doctors
            // -------------------------
            String[] doctorNames = {
                    "Tomas Cerny",
                    "Anna Smith",
                    "Tara Patel",
                    "Katie Nguyen",
                    "John Garcia",
                    "Ben Johnson",
                    "Ken Lee",
                    "Leah Brown",
                    "Chris Martinez",
                    "Owen Wilson",
                    "Chris Kim",
                    "Camry Anderson",
                    "Elliot Thomas",
                    "Elijah Moore",
                    "Becca Taylor"
            };

            LocalDate startDate = LocalDate.of(2026, 5, 4); // Monday

            LocalTime[] slotStartTimes = {
                    LocalTime.of(8, 0),
                    LocalTime.of(9, 30),
                    LocalTime.of(11, 0),
                    LocalTime.of(13, 30),
                    LocalTime.of(15, 0)
            };

            for (int i = 0; i < doctorNames.length; i++) {
                Doctor doctor = new Doctor();
                doctor.setName(doctorNames[i]);
                doctor.setDepartment(departments[i % departments.length]);
                doctorRepository.save(doctor);

                Profile doctorProfile = new Profile();
                doctorProfile.setUsername("doctor" + (i + 1));
                doctorProfile.setEmail("doctor" + (i + 1) + "@example.com");
                doctorProfile.setPassword(passwordEncoder.encode("password"));
                doctorProfile.setRole("DOCTOR");
                doctorProfile.setDoctor(doctor);
                profileRepository.save(doctorProfile);

                Schedule schedule = new Schedule();
                schedule.setDoctor(doctor);
                scheduleRepository.save(schedule);

                doctor.setSchedule(schedule);
                doctorRepository.save(doctor);

                // 5 weekdays x 5 slots per day = 25 time slots per doctor
                for (int day = 0; day < 5; day++) {
                    LocalDate currentDate = startDate.plusDays(day);

                    for (int slot = 0; slot < slotStartTimes.length; slot++) {
                        TimeSlot timeSlot = new TimeSlot();
                        timeSlot.setDate(currentDate);
                        timeSlot.setStartTime(slotStartTimes[slot]);
                        timeSlot.setEndTime(slotStartTimes[slot].plusHours(1));
                        timeSlot.setAvailable(true);
                        timeSlot.setSchedule(schedule);
                        timeSlotRepository.save(timeSlot);
                    }
                }
            }

            // -------------------------
            // Patients
            // -------------------------
            String[] patientNames = {
                    "Joseph Corella", "Bryn Neal", "Charlotte Montague", "Miguel Sena",
                    "Noah Davis", "Ava Wilson", "Ethan Martinez", "Sophia Anderson",
                    "Mason Thomas", "Isabella Moore", "Logan Jackson", "Mia White",
                    "Lucas Harris", "Charlotte Martin", "James Thompson", "Amelia Garcia",
                    "Benjamin Clark", "Harper Lewis", "Elijah Robinson", "Evelyn Walker",
                    "Daniel Young", "Abigail Allen", "Henry King", "Emily Wright",
                    "Jackson Scott", "Ella Green", "Sebastian Baker", "Avery Adams",
                    "Matthew Nelson", "Sofia Carter",

                    "Liam Brooks", "Olivia Bennett", "Jacob Rivera", "Emma Collins",
                    "Michael Perez", "Grace Stewart", "David Morris", "Chloe Rogers",
                    "Samuel Reed", "Hannah Cook", "Andrew Morgan", "Lily Bell",
                    "Christopher Murphy", "Zoe Bailey", "Joshua Cooper", "Natalie Richardson",
                    "Ryan Cox", "Victoria Howard", "Nathan Ward", "Aria Peterson",
                    "Aaron Gray", "Scarlett Ramirez", "Isaac James", "Ellie Watson",
                    "Connor Brooks", "Madeline Sanders", "Caleb Price", "Nora Butler", "Luke Sheridan",
                    "Devon Booker"
            };

            for (int i = 0; i < patientNames.length; i++) {
                Patient patient = new Patient();

                patient.setFirstName(patientNames[i]);

                patientRepository.save(patient);

                Profile patientProfile = new Profile();
                patientProfile.setUsername("patient" + (i + 1));
                patientProfile.setEmail("patient" + (i + 1) + "@example.com");
                patientProfile.setPassword(passwordEncoder.encode("password"));
                patientProfile.setRole("PATIENT");
                patientProfile.setPatient(patient);
                profileRepository.save(patientProfile);
            }

            // -------------------------
            // Appointments
            // -------------------------
            List<Patient> patients = new ArrayList<>(patientRepository.findAll());
            List<TimeSlot> timeSlots = new ArrayList<>(timeSlotRepository.findAllByOrderByDateAscStartTimeAsc());

            Collections.shuffle(patients);
            Collections.shuffle(timeSlots);

            String[] reasons = {
                    "Chest pain",
                    "Annual checkup",
                    "X-ray consultation",
                    "Follow-up visit",
                    "Pediatric wellness visit",
                    "Blood pressure concerns",
                    "Cancer screening",
                    "Emergency evaluation",
                    "Lab results review",
                    "Medication consultation"
            };

            int appointmentCount = 90;

            for (int i = 0; i < appointmentCount; i++) {
                Patient patient = patients.get(i % patients.size());
                TimeSlot timeSlot = timeSlots.get(i);

                Appointment appointment = new Appointment();
                appointment.setAppointmentDate(timeSlot.getDate());
                appointment.setReasonForVisit(reasons[i % reasons.length]);
                appointment.setStatus("CONFIRMED");
                appointment.setCost(150);
                appointment.setRoomNum(400 + i);
                appointment.setTimeslot(timeSlot);
                appointment.setPatient(patient);
                appointmentRepository.save(appointment);

                timeSlot.setAvailable(false);
                timeSlot.setAppointment(appointment);
                timeSlotRepository.save(timeSlot);

                hospital.incrementOccupancy();
            }

            hospitalRepository.save(hospital);

            System.out.println("Database Seeded Successfully");
            System.out.println("Departments created: 5");
            System.out.println("Doctors created: 15");
            System.out.println("Patients created: 60");
            System.out.println("Time slots created: 375");
            System.out.println("Appointments created: 90");
            System.out.println("Doctor usernames: doctor1 - doctor15");
            System.out.println("Patient usernames: patient1 - patient30");
            System.out.println("Password for all accounts: password");
        }
    }
}