/**
* Service class responsible for handling appointment-related business logic.
*
 *  This class enforces hospital occupancy rules:
 *  - Increments occupancy when appointment is confirmed (requirement 7.1)
 *  - Decrements occupancy when appointment is completed/canceled (requirement 7.2)
 *  - Ensures occupancy excludes doctors/nurses by only reflecting # of patient appointments (requirement 7.3)
 *
 *  Author: Bryn Neal
 */


package SFWE405.project.code.Services;

import SFWE405.project.code.InsufficientInfoException;
import SFWE405.project.code.OccupancyMetException;
import SFWE405.project.code.TimeSlotTakenException;
import SFWE405.project.code.Entities.Appointment;
import SFWE405.project.code.Entities.Hospital;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Entities.Schedule;
import SFWE405.project.code.Entities.TimeSlot;
import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Repositories.AppointmentRepository;
import SFWE405.project.code.Repositories.DoctorRepository;
import SFWE405.project.code.Repositories.HospitalRepository;
import SFWE405.project.code.Repositories.TimeSlotRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepo;
    private final TimeSlotRepository TimeslotRepo;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              HospitalRepository hospitalRepository, DoctorRepository doctorRepo, TimeSlotRepository TimeslotRepo) {
        this.appointmentRepository = appointmentRepository;
        this.hospitalRepository = hospitalRepository;
        this.doctorRepo = doctorRepo;
        this.TimeslotRepo = TimeslotRepo;
    }

    /**
     * Updates the status of an appointment and adjusts hospital occupancy accordingly.
     *
     * This method enforces the following requirements:
     * - 7.1: Increment occupancy when an appointment becomes CONFIRMED
     * - 7.2: Decrement occupancy when a CONFIRMED appointment becomes COMPLETED or CANCELLED
     *
     * @param appointmentId the ID of the appointment to update
     * @param newStatus the new status to assign to the appointment
     * @return the updated Appointment object
     * @throws RuntimeException if the appointment or hospital is not found
     */
    @Transactional
    public Appointment updateAppointmentStatus(Long appointmentId, String newStatus) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Hospital hospital = hospitalRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        String oldStatus = appointment.getStatus();

        // Implement Requirement 7.1
        // Increment hospital occupancy when appointment is CONFIRMED
        if (!"CONFIRMED".equals(oldStatus) && "CONFIRMED".equals(newStatus)) {
            hospital.incrementOccupancy();

            // Implement Requirement 3.5
            // Close timeslot when appointment is CONFIRMED
            TimeSlot ts = appointment.getTimeslot();
            if (ts != null) {
                ts.setAvailable(false);
            }
        }

        // Requirement 7.2
        // Decrement hospital occupancy when appointment becomes COMPLETED or CANCELED
        if ("CONFIRMED".equals(oldStatus) &&
                ("COMPLETED".equals(newStatus) || "CANCELED".equals(newStatus))) {
            hospital.decrementOccupancy();
        }

        appointment.setStatus(newStatus);

        return appointment;
    }

    /**
     * Checks that an Appointment mets the criteria before sceduling it.
     *
     * This method enforces the following requirements:
     * 3.1: The system shall reject an appointment request if the Hospital occupancy has reached its maximum capacity.
     * 3.2: The system shall not allow an appointment to be created without a specified doctor, patient, and department.
     * 3.3: The system shall only allow an appointment to be made with a doctor belonging to the department specified by the patient.
     * 3.4: The system shall prevent an appointment from being scheduled if the selected time slot in the Schedule is not marked as "Open." 
     * @return the new Appointment object
     * @throws OccupancyMetException if the hospitals occupancy has been met
     * @throws InsufficientInfoException if the patient, doctor, or department is missing
     * @throws TimeSlotTakenException if the time slot chosen has been taken
     * Author: Charlotte Montague
     */
    
    @Transactional
    public Appointment schedule(Appointment app, Long id) throws OccupancyMetException, InsufficientInfoException, TimeSlotTakenException{
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found"));

        //Implement Requirement 3.1
        //The system shall reject an appointment request if the Hospital occupancy has reached its maximum capacity.
        checkOccupancy(hospital);

        //Implement Requirement 3.2
        //The system shall not allow an appointment to be created without a specified doctor, patient, and department.
        checkSufficientInfo(app);

        //Implement Requirement 3.3
        //The system shall only allow an appointment to be made with a doctor belonging to the department specified by the patient.
        Doctor doctor = doctorRepo.findById(app.getDoctor().getId()).orElseThrow(() -> new RuntimeException("Doctor not found"));
        checkDepartment(doctor, app);

        //Implement Requirement 3.4
        //The system shall prevent an appointment from being scheduled if the selected time slot in the Schedule is not marked as "Open."
        TimeSlot ts = TimeslotRepo.findById(app.getTimeslot().getId()).orElseThrow(() -> new RuntimeException("Time slot not found"));
        checkTimeSlot(ts);

        return appointmentRepository.save(app);
    } 

    //checks if the Hospitals Occupancy is equal to its capacity
    public void checkOccupancy(Hospital hospital) throws OccupancyMetException{
        if(hospital.getOccupancy() == hospital.getCapacity()){
            throw new OccupancyMetException("Hospital Occupency Met");
        }
    }

    //checks a doctor, patient, and department are specified
    public void checkSufficientInfo(Appointment app) throws InsufficientInfoException{
        if(app.getDoctor() == null || app.getPatient() == null || app.getDepartment() == null){
            throw new InsufficientInfoException("Either Doctor, Patient, or Department wasn't specified.");
        }
    }

    //checks that the doctor's department is equal to the department specified
    public void checkDepartment(Doctor d, Appointment app){
        if(!d.getDepartment().getId().equals(app.getDepartment().getId())){
            throw new InputMismatchException("Chosen Doctor's department doesn't match one chosen.");
        }
    }

    //checks that the time slot chosen is open
    public void checkTimeSlot(TimeSlot ts) throws TimeSlotTakenException{
        if(!ts.getAvailable()){
            throw new TimeSlotTakenException("Chosen Time Slot is unavailable");
        }
    }
    /**
     * Requirement 1.1: The system shall allow patients to schedule appointments.
     * Integrated with 3.1-3.4 (Occupancy and Info checks). Author: Miguel Sena
     */

    @Transactional
    public Appointment schedule(Appointment app, Long hospitalId) throws OccupancyMetException, InsufficientInfoException, TimeSlotTakenException {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        // Requirements 3.1 - 3.4
        checkOccupancy(hospital);
        checkSufficientInfo(app);
        
        Doctor doctor = doctorRepo.findById(app.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        checkDepartment(doctor, app);

        TimeSlot ts = TimeslotRepo.findById(app.getTimeslot().getId())
                .orElseThrow(() -> new RuntimeException("Time slot not found"));
        checkTimeSlot(ts);

        // Requirement 1.1: Set initial status
        app.setStatus("PENDING");
        
        return appointmentRepository.save(app);
    }

    /**
     * Requirement 1.2: The system shall allow patients to edit their pending or confirmed appointments.
     */
    @Transactional
    public Appointment editAppointment(Long appointmentId, TimeSlot newTimeSlot) throws TimeSlotTakenException {
        Appointment appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Business Rule: Cannot edit if already checked in or completed
        if ("CHECKED_IN".equals(appt.getStatus()) || "COMPLETED".equals(appt.getStatus())) {
            throw new IllegalStateException("Cannot edit an appointment that is already checked in or completed.");
        }

        // Verify the new timeslot is available
        checkTimeSlot(newTimeSlot);

        appt.setTimeslot(newTimeSlot);
        return appointmentRepository.save(appt);
    }

    /**
     * Requirement 1.4: The system shall allow patients to check in for their appointment on the day of the visit.
     */
    @Transactional
    public void checkIn(Long appointmentId) {
        Appointment appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Requirement 1.4: Check-in must be on the same day
        // Assuming Appointment has a getAppointmentDate() or extracted from Timeslot
        LocalDate appointmentDate = appt.getTimeslot().getDate(); 
        if (!appointmentDate.equals(LocalDate.now())) {
            throw new RuntimeException("Check-in is only available on the day of the appointment.");
        }

        // Logic: Transition to CHECKED_IN
        appt.setStatus("CHECKED_IN");
        appointmentRepository.save(appt);
    }

    /**
     * Requirement 1.3: Update profile information is typically handled in a PatientService,
     * but if you are putting it here, it would look like this:
     */
    @Transactional
    public void updatePatientProfile(Patient patient, String newEmail, String newUsername) {
        patient.setEmail(newEmail);
        patient.setUsername(newUsername);
        // Save via a PatientRepository if available
    }
    
    

}

