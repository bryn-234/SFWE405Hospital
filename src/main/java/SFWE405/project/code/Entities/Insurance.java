package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
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
    @OneToMany(mappedBy = "insurance")
    private Set<Patient> patients = new HashSet<>();

    public Insurance() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public double getCoveragePercentage() { return coveragePercentage; }
    public void setCoveragePercentage(double coveragePercentage) { this.coveragePercentage = coveragePercentage; }

    public Set<Patient> getPatients() { return patients; }
    public void setPatients(Set<Patient> patients) { this.patients = patients; }

    public void setName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
}
