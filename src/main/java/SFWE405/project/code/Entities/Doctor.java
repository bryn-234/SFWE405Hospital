package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Doctor(){}

    public Long getId() {return id;}
    
    public void setName(String name) {this.name = "Dr." + name;}
    public String getName() {return name;}

    public void addAppointment(Appointment a) {this.appointments.add(a);}
    public Set<Appointment> getAppointments() {return appointments;}

    public void addDepartment(Department d) {this.department = d;}
    public Department getDepartment() {return department;}
}
