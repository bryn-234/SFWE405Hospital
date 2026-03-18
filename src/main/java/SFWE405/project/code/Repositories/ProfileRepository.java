package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository  extends JpaRepository<Profile, Long> {
    List<Profile> findByPatientId(Long id);
}
