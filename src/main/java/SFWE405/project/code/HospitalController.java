package SFWE405.project.code;

import java.util.List;

import javax.management.DefaultLoaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Repositories.DepartmentRespository;
import SFWE405.project.code.Repositories.DoctorRespository;
import SFWE405.project.code.Repositories.PatientRepository;

@RestController
public class HospitalController {
    @Autowired
    private DoctorRespository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired 
    private DepartmentRespository departmentRepo;

    @GetMapping("/HMS/patients")
    public List<Patient> showPatients(){
        return patientRepo.findAll();
    }

    @PostMapping("/HMS/addPatient")
    public Patient addPatient(@RequestBody Patient p){
        System.out.println("SNN: " + p.getSSN());
        return patientRepo.save(p);
    }

    @GetMapping("/HMS/doctors")
    public List<Doctor> showDoctors(){
        return doctorRepo.findAll();
    }

    @PostMapping("/HMS/addDoctor")
    public Doctor addDoctor(@RequestBody Doctor d){
        return doctorRepo.save(d);
    }

    
}
