package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient {
    @Id
    private Long ssn;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private char sex;

    @OneToMany
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @OneToOne
    private MedicalRecord medicalRecord = new MedicalRecord();

    @ManyToOne
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
