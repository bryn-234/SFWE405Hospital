package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis = new String();
    private String treatmentPlan = new String();
    private LocalDate lastUpdated;

    @ElementCollection
    private Set<String> prescriptions = new HashSet<>();

    @ElementCollection
    private Set<String> allergies = new HashSet<>();

    @ElementCollection
    private Set<String> vaccines = new HashSet<>();

    @ElementCollection
    private Set<String> conditions = new HashSet<>();

    //Commenting this out to match the current domain diagram we have. 
    // We can always add it back in later if we decide to include it.
    /*@ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor recordedBy;
    */

    @OneToOne(mappedBy = "medicalRecord")
    private Patient patient;
}
