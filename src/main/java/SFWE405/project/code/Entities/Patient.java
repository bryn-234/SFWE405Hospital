package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    
    private LocalDate birthDate;
    private String phoneNumber;
    private String sex;
    @Lob
    private String medicalRecord;
    @Lob
    private String prescribedMedications;
    
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();


}
