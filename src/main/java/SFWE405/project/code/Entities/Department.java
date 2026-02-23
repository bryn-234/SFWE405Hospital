package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "department")
    private Set<Doctor> doctors = new HashSet<>();

    //Constructors
    public Department() {}

    //getters and setters
    public Long getId() { return id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void addDoctor(Doctor d) { this.doctors.add(d); }
    public Set<Doctor> getDoctors() { return doctors; }
    
    public void addHospital(Hospital h) { this.hospital = h; }
    public Hospital getHospital() { return hospital; }
    
}
