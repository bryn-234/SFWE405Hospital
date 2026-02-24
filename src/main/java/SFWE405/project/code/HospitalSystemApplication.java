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
import jakarta.persistence.criteria.CriteriaBuilder.In;
import SFWE405.project.code.Repositories.DepartmentRepository;
import SFWE405.project.code.Repositories.DoctorRepository;

@SpringBootApplication
public class HospitalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalSystemApplication.class, args);
	}
}
