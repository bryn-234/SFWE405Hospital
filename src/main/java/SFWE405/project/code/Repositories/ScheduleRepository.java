package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Schedule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);
}
