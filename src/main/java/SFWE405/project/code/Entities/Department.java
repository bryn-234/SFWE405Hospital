package SFWE405.project.code.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnoreProperties("departments")
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @JsonIgnoreProperties("department")
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Doctor> doctors = new HashSet<>();

    public void addDoctor(Doctor d){
        doctors.add(d);
    }
}
