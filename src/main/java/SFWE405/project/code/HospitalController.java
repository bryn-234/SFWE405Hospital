package SFWE405.project.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.project.code.DTOs.MedicalRecordRequest;
import SFWE405.project.code.DTOs.MedicationRequest;
import SFWE405.project.code.Entities.Appointment;
import SFWE405.project.code.Entities.Department;
import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Hospital;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Entities.Schedule;
import SFWE405.project.code.Entities.TimeSlot;
import SFWE405.project.code.Repositories.AppointmentRepository;
import SFWE405.project.code.Repositories.DepartmentRepository;
import SFWE405.project.code.Repositories.DoctorRepository;
import SFWE405.project.code.Repositories.HospitalRepository;
import SFWE405.project.code.Repositories.PatientRepository;
import SFWE405.project.code.Services.AppointmentService;
import SFWE405.project.code.Services.DoctorService;
import SFWE405.project.code.Services.HospitalService;

@RestController
public class HospitalController {
    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired 
    private DepartmentRepository departmentRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired 
    private HospitalRepository hospitalRepo;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/HMS/patients")
    public List<Patient> showPatients(){
        return patientRepo.findAll();
    }

    @PostMapping("/HMS/addPatient")
    public Patient addPatient(@RequestBody Patient p){
        return patientRepo.save(p);
    }

    @GetMapping("/HMS/doctors")
    public List<Doctor> showDoctors(){
        return (List<Doctor>) doctorRepo.findAll();
    }

    @PostMapping("/HMS/{id}/addDoctor")
    public void addDoctor(@PathVariable Long id, @RequestBody Doctor d){
        hospitalService.addDoctor(d, id);
    }

    @GetMapping("/HMS/departments")
    public List<Department> getDerpartments(){
        return (List<Department>) departmentRepo.findAll();
    }

    @PostMapping("/HMS/{id}/addDepartment")
    public void addDepartment(@PathVariable Long id, @RequestBody Department d){
        hospitalService.addDepartment(d, id);
    }

    @GetMapping("/HMS/{id}/hospital")
    public Hospital getHospitals(@PathVariable Long id){
        return hospitalRepo.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found")) ;
    }

    @PostMapping("/HMS/addHospital")
    public Hospital addHospital(@RequestBody Hospital h){
        return hospitalRepo.save(h);
    }

    @PostMapping("/HMS/{id}/addSchedule")
    public void addSchedule(@PathVariable Long id, @RequestBody Schedule s){
        hospitalService.addSchedule(s, id);
    }

    @PostMapping("/HMS/{id}/addTimeSlot")
    public void addTimeSlot(@PathVariable Long id, @RequestBody TimeSlot t){
        hospitalService.addTimeSlot(t, id);
    }

    @PostMapping("/HMS/{id}/schedule-Appointment")
    public Appointment scheduleAppointment(@PathVariable Long id, @RequestBody Appointment a) throws OccupancyMetException, InsufficientInfoException, TimeSlotTakenException{
        appointmentService.schedule(a, id);
        return appointmentRepo.save(a);
    }

    @PostMapping("/HMS/{id}/edit-Appointment")
    public Appointment editAppointment(@PathVariable Long id, @RequestBody Appointment a){
        appointmentService.updateAppointmentStatus(id, a.getStatus());
        return appointmentRepo.save(a);
    }

    @PostMapping("/HMS/{doctorId}/{patientId}/prescribeMedication")
    public Patient prescribeMedication(@PathVariable Long doctorId, @PathVariable Long patientId,
            @RequestBody MedicationRequest request) {
        return doctorService.prescribeMedication(doctorId, patientId, request.getMedication());
    }

    @GetMapping("/HMS/{doctorId}/{patientId}/medicalRecord")
    public String readMedicalRecord(@PathVariable Long doctorId, @PathVariable Long patientId) {
        return doctorService.readMedicalRecord(doctorId, patientId);
    }

    @PostMapping("/HMS/{doctorId}/{patientId}/updateMedicalRecord")
    public Patient updateMedicalRecord(@PathVariable Long doctorId, @PathVariable Long patientId,
            @RequestBody MedicalRecordRequest request) {
        return doctorService.updateMedicalRecord(doctorId, patientId, request.getMedicalRecord());
    }
}
