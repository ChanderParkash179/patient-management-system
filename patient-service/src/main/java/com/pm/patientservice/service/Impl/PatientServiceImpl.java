package com.pm.patientservice.service.Impl;

import com.pm.patientservice.dtos.patient.request.PatientRequest;
import com.pm.patientservice.dtos.patient.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import com.pm.patientservice.exceptions.AlreadyExistsException;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PaginationResponse<PatientResponse> findAllPatients(Integer pageNo, Integer pageSize) {
        log.info("finding all patients");
        log.info("request: pageNo: {}, pageSize: {}", pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Patient> patientPage = this.patientRepository.findAllByIsActiveTrue(pageable);

        log.info("Patient fetched Successfully");

        if (patientPage.getContent().isEmpty()) {
            log.info("No Patients found");
            return PaginationResponse.makeEmptyResponse();
        }

        log.info("Total Patients found: {}", patientPage.getContent().size());

        return PaginationResponse.makeResponse(patientPage, PatientResponse::new);
    }

    @Override
    public PatientResponse createPatient(PatientRequest request) {
        log.info("creating new patient");
        log.info("request patient: {}", request);

        log.info("validating request patient record");
        if (this.patientRepository.findPatientByEmail(request.getEmail()).isPresent())
            throw new AlreadyExistsException("patient is already available against given email: " + request.getEmail());

        log.info("creating new patient");
        Patient patient = Patient.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress() != null ? request.getAddress() : null)
                .isActive(Boolean.TRUE)
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .registeredDate(LocalDate.parse(request.getRegistrationDate()))
                .build();

        Patient saved = this.patientRepository.save(patient);
        log.info("patient created successfully");

        return new PatientResponse(saved);
    }
}