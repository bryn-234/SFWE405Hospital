package com.example.code.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.code.Entities.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByLastName(String lastName);

    List<Patient> findByFirstName(String firstName);
}
