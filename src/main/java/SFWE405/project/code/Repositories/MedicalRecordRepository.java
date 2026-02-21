package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MedicalRecordRepository {
    List<MedicalRecord> findByPatientId(Long patientId);
}
