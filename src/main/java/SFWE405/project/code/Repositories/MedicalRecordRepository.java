package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.MedicalRecord;
import SFWE405.project.code.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository {
    List<MedicalRecord> findByPatientId(Long patientId);
}
