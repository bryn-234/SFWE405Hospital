package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ssn;

    private String firstName;
    private String lastName;
    
    private LocalDate birthDate;
    private String sex;
    
    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;    
}
