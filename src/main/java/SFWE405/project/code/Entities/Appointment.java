package SFWE405.project.code.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate appointmentDate;
    private String reasonForVisit;
    private String status;
    private int cost;
    private int roomNum;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    //I commented this out to match the domain diagram we currently have. 
    //We can always add it back in later if we decide to include it.
    /*@ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;*/

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    //Constructors
    public Appointment() {}

    //Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) {this.appointmentDate = appointmentDate; }
    public String getReasonForVisit() { return reasonForVisit; }
    public void setReasonForVisit(String reasonForVisit) {this.reasonForVisit = reasonForVisit;}
    public String getStatus() { return status; }
    public void setStatus(String status) {this.status = status; }
    public int getCost() {return cost;}
    public void setCost(int cost) {this.cost = cost;}
    public int getRoomNum() {return roomNum;}
    public void setRoomNum(int roomNum) {this.roomNum = roomNum;}
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
   
    //I commented this out to match the domain diagram we currently have

    //public Department getDepartment() { return department; }
    //public void setDepartment(Department department) { this.department = department; }
                                               
}
