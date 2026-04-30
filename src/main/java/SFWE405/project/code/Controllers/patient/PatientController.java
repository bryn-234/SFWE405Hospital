package SFWE405.project.code.Controllers.patient;

import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Entities.Profile;
import SFWE405.project.code.Entities.TimeSlot;
import SFWE405.project.code.InsufficientInfoException;
import SFWE405.project.code.OccupancyMetException;
import SFWE405.project.code.TimeSlotTakenException;
import SFWE405.project.code.Entities.Appointment;
import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Hospital;
import SFWE405.project.code.Repositories.AppointmentRepository;
import SFWE405.project.code.Repositories.DoctorRepository;
import SFWE405.project.code.Repositories.TimeSlotRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import SFWE405.project.code.Services.AppointmentService;
import SFWE405.project.code.Services.AuthService;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private TimeSlotRepository timeSlotRepo;

    @Autowired 
    private AppointmentRepository appointmentRepo;

    @Autowired 
    private AppointmentService appService;

    @GetMapping("/home")
    public String patientHome(Model model) {

        Profile profile = authService.getLoggedInProfile();
        Patient patient = profile.getPatient();
        List<Doctor> doctors = doctorRepo.findAll();

        model.addAttribute("profile", profile);
        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctors);

        return "patient/home";
    }

    @GetMapping("/timeslots/{doctorId}")
    @ResponseBody
    public List<TimeSlot> getTimeslots(@PathVariable Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        return doctor.getSchedule().getTimeSlot().stream().filter(ts -> Boolean.TRUE.equals(ts.getAvailable())).collect(Collectors.toList());
    }

    @PostMapping("/schedule")
    public String scheduleAppointment(@RequestParam Long timeslotId, @RequestParam Long doctorId, Model model) throws OccupancyMetException, InsufficientInfoException, TimeSlotTakenException {

        Profile profile = authService.getLoggedInProfile();
        Patient patient = profile.getPatient();
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Hospital hospital = doctor.getDepartment().getHospital();

        TimeSlot timeslot = timeSlotRepo.findById(timeslotId).orElseThrow(() -> new RuntimeException("Timeslot not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setTimeslot(timeslot);
        appointment.setDoctor(doctor);
        appointment.setStatus("PENDING");

        appService.schedule(appointment, hospital.getId());

        timeslot.setAvailable(false);
        timeSlotRepo.save(timeslot);

        appointmentRepo.save(appointment);

        return "redirect:/patient/home";
    }


}
