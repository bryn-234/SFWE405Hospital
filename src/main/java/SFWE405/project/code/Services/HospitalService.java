package SFWE405.project.code.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SFWE405.project.code.Entities.Department;
import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Hospital;
import SFWE405.project.code.Entities.Schedule;
import SFWE405.project.code.Entities.TimeSlot;
import SFWE405.project.code.Repositories.DepartmentRespository;
import SFWE405.project.code.Repositories.DoctorRespository;
import SFWE405.project.code.Repositories.HospitalRepository;
import SFWE405.project.code.Repositories.ScheduleRepository;
import SFWE405.project.code.Repositories.TimeSlotRepository;

/**
* Service class responsible for handling hospital related business logic and initializing 
* doctor, department, and hospital.
*
* Needed for Postman testing to work  
*
*  Author: Charlotte Montague
*/

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepo;

    @Autowired
    private DepartmentRespository departmentRepo;

    @Autowired
    private DoctorRespository doctorRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private TimeSlotRepository timeslotRepo;

    public Department addDepartment(Department d, Long id){
        Hospital h = hospitalRepo.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found"));
        d.setHospital(h);
        h.addDepartment(d);
        return departmentRepo.save(d);
    }

    public Doctor addDoctor(Doctor d, Long id){
        Department dep = departmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        d.setDepartment(dep);
        dep.addDoctor(d);
        return doctorRepo.save(d); 
    }

    public Schedule addSchedule(Schedule s, Long id){
        Doctor d = doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        s.setDoctor(d);
        d.setSchedule(s);
        return scheduleRepo.save(s);
    }

    public TimeSlot addTimeSlot(TimeSlot t, Long id){
        Schedule s = scheduleRepo.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        t.setSchedule(s);
        s.addTimeSlot(t);
        return timeslotRepo.save(t);
    }

}
