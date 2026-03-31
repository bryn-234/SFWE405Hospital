package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate appointmentDate;
    private String reasonForVisit;
    private String status;
    private Integer cost;
    private Integer roomNum;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties({"appointments", "department", "schedule"})
    private Doctor doctor;

    //Need this for requirements 3.2 and 3.3
    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties({"doctors", "hospital"})
    private Department department;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties({"appointments"})
    private Patient patient; 

    //Needed for requirement 3.4
    @OneToOne
    @JoinColumn(name = "timeslot_id")
    @JsonIgnoreProperties({"appointment", "schedule"})
    private TimeSlot timeslot;
}
