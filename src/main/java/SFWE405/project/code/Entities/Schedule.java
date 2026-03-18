package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

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

    private Boolean isOpen;
    private String availability;

    @OneToMany(mappedBy = "timeSlot")
    private Set<TimeSlot> timeSlot = new HashSet<>();

    @OneToOne
    private Doctor doctor;

    
}
