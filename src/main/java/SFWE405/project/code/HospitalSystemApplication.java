package SFWE405.project.code;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import SFWE405.project.code.Entities.Appointment;
import SFWE405.project.code.Entities.Department;
import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Hospital;
import SFWE405.project.code.Entities.Insurance;
import SFWE405.project.code.Entities.MedicalRecord;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Repositories.AppointmentRepository;
import SFWE405.project.code.Repositories.HospitalRepository;
import SFWE405.project.code.Repositories.InsuranceRepository;
import SFWE405.project.code.Repositories.MedicalRecordRepository;
import SFWE405.project.code.Repositories.PatientRepository;
import SFWE405.project.code.Repositories.DepartmentRepository;
import SFWE405.project.code.Repositories.DoctorRepository;

@SpringBootApplication
public class HospitalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalSystemApplication.class, args);
	}

	@Bean
CommandLineRunner seedDatabase(
    HospitalRepository hospitalRepo,
    DepartmentRepository deptRepo,
    InsuranceRepository insuranceRepo,
    DoctorRepository doctorRepo,
    PatientRepository patientRepo,
    MedicalRecordRepository mrRepo,
    AppointmentRepository appointRepo) {

    return args -> {
        // 1. Create Hospital
        Hospital hospital = new Hospital();
        hospital.setName("General Health Memorial");
        hospital.setCapacity(500);
        hospitalRepo.save(hospital);

        // 2. Create Insurances
        List<String> insNames = List.of("Blue Cross Blue Shield", "United Healthcare", "Access");
        List<Insurance> insurances = insNames.stream().map(name -> {
            Insurance i = new Insurance();
            i.setProviderName(name);
            return insuranceRepo.save(i);
        }).toList();

        // 3. Create Departments
        List<String> deptNames = List.of("ER", "Endocrinology", "Pediatrics");
		List<Department> departments = new ArrayList<>();
        for(int i=0; i<deptNames.size(); i++) {
			Department d = new Department();
			d.setName(deptNames.get(i));
			d.addHospital(hospital);
			deptRepo.save(d);
			departments.add(d);
		}

        // 4. Create 10 Doctors
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            Doctor doc = new Doctor();
            doc.setName("Dr. Smith " + i);
            doc.addDepartment(departments.get(random.nextInt(departments.size())));
            doctorRepo.save(doc);
        }

        // 5. Create 5 Patients & Medical Records
        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            MedicalRecord mr = new MedicalRecord();
            mr.addAllergies("Pollon");
            mr.addConditions("Healthy");
            mr = mrRepo.save(mr); // Save MR first

            Patient p = new Patient();
            p.setFirstName("Patient" + i);
            p.setLastName("Doe");
            p.setBrithday((LocalDate.of(1990 + i, 1, 1)));
            p.setSex('M');
            p.addMedicalRecord(mr);
            p.addInsurance(insurances.get(random.nextInt(insurances.size())));
            patients.add(patientRepo.save(p));
        }

        // 6. Create 2 Appointments
        List<Doctor> doctors = (List<Doctor>) doctorRepo.findAll();
        for (int i = 0; i < 2; i++) {
            Appointment appt = new Appointment();
            appt.setAppointmentDate(LocalDate.now().plusDays(i + 1));
            appt.setRoomNum(100 + i);
            appt.setDoctor(doctors.get(i)); // Assign first two docs
            appt.setPatient(patients.get(i)); // Assign first two patients
            appointRepo.save(appt);
        }

        System.out.println("--- Database Seeded Successfully for Tuesday Demo! ---");
    };
}

}
