package com.example.code.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private String treatmentPlan;
    private String prescriptions;
    private LocalDate lastUpdated;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor recordedBy;

    //Constructors
    public MedicalRecord() {}

    //Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatmentPlan() { return treatmentPlan; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }
    public String getPrescriptions() { return prescriptions; }
    public void setPrescriptions(String prescriptions) { this.prescriptions = prescriptions; }
    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }
    //public Patient getPatient() { return patient; }
    //public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getRecordedBy() { return recordedBy; }
    public void setRecordedBy(Doctor recordedBy) { this.recordedBy = recordedBy; }
    
}
