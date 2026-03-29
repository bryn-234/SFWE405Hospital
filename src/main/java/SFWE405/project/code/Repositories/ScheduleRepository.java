package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findById(long id);
}
