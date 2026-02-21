package com.example.code.Repositories;

import org.springframework.stereotype.Repository;

import com.example.code.Entities.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepository {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findbyDoctorId(Long doctorId);
}
