package SFWE405.project.code.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SFWE405.project.code.Entities.Doctor;
import SFWE405.project.code.Entities.Patient;
import SFWE405.project.code.Repositories.DoctorRepository;
import SFWE405.project.code.Repositories.PatientRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient prescribeMedication(Long doctorId, Long patientId, String medication) {
        validateTextValue(medication, "Medication");
        requireDoctorWithDepartment(doctorId);
        Patient patient = findPatient(patientId);

        String existingMedications = patient.getPrescribedMedications();
        if (existingMedications == null || existingMedications.isBlank()) {
            patient.setPrescribedMedications(medication.trim());
        } else {
            patient.setPrescribedMedications(existingMedications + System.lineSeparator() + medication.trim());
        }

        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public String readMedicalRecord(Long doctorId, Long patientId) {
        requireDoctorWithDepartment(doctorId);
        Patient patient = findPatient(patientId);
        return patient.getMedicalRecord() == null ? "" : patient.getMedicalRecord();
    }

    @Transactional
    public Patient updateMedicalRecord(Long doctorId, Long patientId, String medicalRecord) {
        validateTextValue(medicalRecord, "Medical record");
        requireDoctorWithDepartment(doctorId);
        Patient patient = findPatient(patientId);
        patient.setMedicalRecord(medicalRecord.trim());
        return patientRepository.save(patient);
    }

    private Doctor requireDoctorWithDepartment(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (doctor.getDepartment() == null) {
            throw new IllegalStateException("Doctor must be associated with a department.");
        }

        return doctor;
    }

    private Patient findPatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    private void validateTextValue(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }
}
