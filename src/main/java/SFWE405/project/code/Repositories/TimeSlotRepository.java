package SFWE405.project.code.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SFWE405.project.code.Entities.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    TimeSlot findById(long id);
}
