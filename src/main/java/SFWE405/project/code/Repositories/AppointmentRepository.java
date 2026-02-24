package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Appointment;
import SFWE405.project.code.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long id);
    List<Appointment> findByDoctorId(Long doctorId);
}
