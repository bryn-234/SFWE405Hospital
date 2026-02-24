package SFWE405.project.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.project.code.Entities.Department;
import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Repositories.DepartmentRepository;
import SFWE405.project.code.Repositories.DoctorRepository;
import SFWE405.project.code.Repositories.PatientRepository;

@RestController
@RequestMapping("/HMS")
public class HospitalController {
    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired 
    private DepartmentRepository departmentRepo;
    
    @GetMapping("/patients")
    public List<Patient> getPatients(){
        return patientRepo.findAll();
    }

    @PostMapping("/addPatient")
    public Patient addPatient(@RequestBody Patient p){
        System.out.println(p);
        return patientRepo.save(p);
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(){
        return (List<Doctor>) doctorRepo.findAll();
    }

    @PostMapping("/addDoctor")
    public Doctor addDoctor(@RequestBody Doctor d){
        return doctorRepo.save(d);
    }

    @GetMapping("/departments")
    public List<Department> getDerpartments(){
        return (List<Department>) departmentRepo.findAll();
    }

    @PostMapping("/addDepartment")
    public Department addDepartment(@RequestBody Department d){
        return departmentRepo.save(d);
    }
}
