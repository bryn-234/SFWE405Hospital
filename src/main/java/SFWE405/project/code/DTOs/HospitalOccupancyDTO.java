package SFWE405.project.code.DTOs;

public class HospitalOccupancyDTO {

    private String hospitalName;
    private int occupancy;
    private int capacity;
    private int availableSpace;

    public HospitalOccupancyDTO(String hospitalName, int occupancy, int capacity) {
        this.hospitalName = hospitalName;
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.availableSpace = capacity - occupancy;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }
}
