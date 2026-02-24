package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Hospital findByName(String name);
}
