package com.example.code.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.code.Entities.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepository {
    List<MedicalRecord> findByPatientId(Long patientId);
}
