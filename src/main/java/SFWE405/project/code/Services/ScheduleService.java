package SFWE405.project.code.Services;

import SFWE405.project.code.Entities.Schedule;
import SFWE405.project.code.Entities.TimeSlot;
import SFWE405.project.code.Repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to handle business logic related to Doctor's Schedule and TimeSlots.
 * This includes sorting time slots, filtering by month, creating new slots, and toggling availability.
 * 
 * 
 * @author Joseph Corella
 */
@Service
public class ScheduleService {

    @Autowired
    private TimeSlotRepository tsRepo;

    public List<TimeSlot> getSortedSlots(Schedule schedule) {
        return schedule.getTimeSlot().stream()
                .sorted(Comparator.comparing(TimeSlot::getDate)
                        .thenComparing(TimeSlot::getStartTime))
                .collect(Collectors.toList());
    }

    public List<TimeSlot> getSlotsForMonth(Schedule schedule, int year, int month) {
        return schedule.getTimeSlot().stream()
                .sorted(Comparator.comparing(TimeSlot::getDate)
                                .thenComparing(TimeSlot::getStartTime))
                .filter(ts -> ts.getDate().getYear() == year && ts.getDate().getMonthValue() == month)
                .collect(Collectors.toList());
    }

    public void createAndSaveSlot(Schedule schedule, String date, String start, String end) throws Exception {
        LocalDate parsedDate;
        LocalTime startTime;
        LocalTime endTime;

        // 1. Validate Formats
        try {
            parsedDate = LocalDate.parse(date);
            startTime = LocalTime.parse(start);
            endTime = LocalTime.parse(end);
        } catch (Exception e) {
            throw new Exception("Invalid format. Please use YYYY-MM-DD for dates and HH:mm (24hr) for times.");
        }

        // 2. Logical Checks
        if (parsedDate.isBefore(LocalDate.now())) {
            throw new Exception("You cannot create a slot in the past.");
        }

        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            throw new Exception("End time must be after the start time.");
        }

        if(schedule.getTimeSlot().stream().anyMatch(ts -> ts.getDate().equals(parsedDate) &&
                ((startTime.isBefore(ts.getEndTime()) && startTime.isAfter(ts.getStartTime())) ||
                 (endTime.isBefore(ts.getEndTime()) && endTime.isAfter(ts.getStartTime())) ||
                 (startTime.equals(ts.getStartTime()) || endTime.equals(ts.getEndTime()))))) {
            throw new Exception("This time slot overlaps with an existing slot.");
        }

        // 3. Save if all checks pass
        TimeSlot newSlot = new TimeSlot();
        newSlot.setDate(parsedDate);
        newSlot.setStartTime(startTime);
        newSlot.setEndTime(endTime);
        newSlot.setAvailable(true);
        newSlot.setSchedule(schedule);
        tsRepo.save(newSlot);
    }

    public void toggleSlotAvailability(Long slotId) throws Exception {
        TimeSlot ts = tsRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        
        // Check if there is an appointment attached
        if (ts.getAppointment() != null) {
            throw new Exception("This slot is already booked and cannot be toggled.");
        }
        
        ts.setAvailable(!ts.getAvailable());
        tsRepo.save(ts);
    }
}