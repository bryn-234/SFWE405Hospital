package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private int cost;
    private int roomNum;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    //I commented this out to match the domain diagram we currently have. 
    //We can always add it back in later if we decide to include it.
    /*@ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;*/

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;                   
}
