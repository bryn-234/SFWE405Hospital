package SFWE405.project.code.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private int capacity;
    private int occupancy;

    @OneToMany(mappedBy = "hospital")
    private Set<Department> departments = new HashSet<>();

    public void incrementOccupancy() {
        if (occupancy < capacity) {
            occupancy++;
        } else {
            throw new IllegalStateException("Hospital is at full capacity.");
        }
    }

    public void decrementOccupancy() {
        if(occupancy > 0) {
            occupancy--;
        }
    }

}
