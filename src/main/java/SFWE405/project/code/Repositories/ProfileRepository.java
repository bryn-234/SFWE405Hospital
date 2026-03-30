package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Profile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository  extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username);
}
