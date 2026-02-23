package SFWE405.project.code.Entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
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

    public Hospital() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getOccupancy() { return occupancy; }
    public void setOccupancy(int occupancy) { this.occupancy = occupancy; }

    public Set<Department> getDepartments() { return departments; }
    public void setDepartments(Set<Department> departments) { this.departments = departments; }
}
