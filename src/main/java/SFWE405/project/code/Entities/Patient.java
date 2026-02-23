package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ssn;
    

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private char sex;

    @JsonManagedReference
    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord = new MedicalRecord();

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance = new Insurance();

    public Patient(){}

    //getters and Setters
    public void setSSN(Long ssn){this.ssn = ssn;}
    public Long getSSN(){return ssn;}
    
    public void setFirstName(String firstName){this.firstName = firstName;}
    public String getFirstName(){return firstName;}

    public void setLastName(String lastName){this.lastName = lastName;}
    public String getLastName(){return lastName;}

    public void setBrithday(LocalDate birthDate){this.birthDate = birthDate;}
    public LocalDate getBirthday(){return birthDate;}

    public void setSex(char sex){this.sex = sex;}
    public char getSex(){return sex;}

    public void addInsurance(Insurance i){this.insurance = i;}
    public Insurance getInsurance(){return insurance;}

    public void addAppointment(Appointment a){this.appointments.add(a);}
    public Set<Appointment> getAppointments(){return appointments;}

    public void addMedicalRecord(MedicalRecord mr){this.medicalRecord = mr;}
    public MedicalRecord getMedicalRecord(){return medicalRecord;}

}
