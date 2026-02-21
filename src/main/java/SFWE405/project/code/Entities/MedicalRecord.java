package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private String treatmentPlan;
    private Set<String> prescriptions;
    private LocalDate lastUpdated;
    private Set<String> allergies;
    private Set<String> vaccines;
    private Set<String> conditions;


    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor recordedBy;

    @OneToOne
    private Patient patient;

    //Constructors
    public MedicalRecord() {}

    //Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatmentPlan() { return treatmentPlan; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }
    public Set<String> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(String prescriptions) { this.prescriptions.add(prescriptions); }
    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }
    public Set<String> getAllergies() {return allergies;}
    public void addAllergies(String allergy) {this.allergies.add(allergy);}
    public Set<String> getVaccines(){return vaccines;}
    public void addVaccines(String vaccine) {this.vaccines.add(vaccine);}
    public Set<String> getConditions() {return conditions;}
    public void addConditions(String condition) {this.conditions.add(condition);}
    public Patient getPatient() { return patient;}
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getRecordedBy() { return recordedBy; }
    public void setRecordedBy(Doctor recordedBy) { this.recordedBy = recordedBy; }
    
}
