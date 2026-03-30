package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Boolean isOpen;
    //private String availability;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnoreProperties("schedule")
    private Set<TimeSlot> timeSlot = new HashSet<>();

    @OneToOne
    @JsonIgnoreProperties("schedule")
    private Doctor doctor;

    public void addTimeSlot(TimeSlot ts){
        timeSlot.add(ts);
    }
}
