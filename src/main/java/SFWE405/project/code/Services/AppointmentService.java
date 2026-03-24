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

import SFWE405.project.code.Entities.Appointment;
import SFWE405.project.code.Entities.Hospital;
import SFWE405.project.code.Repositories.AppointmentRepository;
import SFWE405.project.code.Repositories.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              HospitalRepository hospitalRepository) {
        this.appointmentRepository = appointmentRepository;
        this.hospitalRepository = hospitalRepository;
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
        }

        // Requirement 7.2
        // Decrement hospital occupancy when appointment becomes COMPLETED or CANCELED
        if ("CONFIRMED".equals(oldStatus) &&
                ("COMPLETED".equals(newStatus) || "CANCELED".equals(newStatus))) {
            hospital.decrementOccupancy();
        }

        appointment.setStatus(newStatus);

        hospitalRepository.save(hospital);
        return appointmentRepository.save(appointment);
    }


}
