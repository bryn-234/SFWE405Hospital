package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;
    private String policyNumber;
    
    //Joseph -- I like this variable, but I think we can move this information into
    //a separate entity that breaks down the insurance coverage details. For now, we can keep it here for simplicity.
    private double coveragePercentage; 

    //Joseph -- One insurance can cover many patients, but each patient can only have one insurance.
    //This was mapped as OneToOne, I changed it to oneToMany to match the domain diagram. 
    @JsonBackReference
    @OneToMany(mappedBy = "insurance")
    private Set<Patient> patients = new HashSet<>();
}
