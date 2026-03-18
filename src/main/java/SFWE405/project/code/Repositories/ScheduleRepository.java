package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByScheduleId(Long id);
}
