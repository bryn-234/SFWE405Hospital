package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByLastName(String lastName);

    List<Patient> findByFirstName(String firstName);
}
