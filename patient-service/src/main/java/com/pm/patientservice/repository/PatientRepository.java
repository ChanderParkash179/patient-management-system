package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Page<Patient> findAllByIsActiveTrue(Pageable pageable);

    Optional<Patient> findPatientByEmail(String email);
}