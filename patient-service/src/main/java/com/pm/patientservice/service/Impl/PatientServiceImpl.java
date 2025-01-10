package com.pm.patientservice.service.Impl;

import com.pm.patientservice.dtos.patient.request.PatientCreateRequest;
import com.pm.patientservice.dtos.patient.request.PatientUpdateRequest;
import com.pm.patientservice.dtos.patient.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import com.pm.patientservice.exceptions.AlreadyExistsException;
import com.pm.patientservice.exceptions.ResourceNotFoundException;
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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PaginationResponse<PatientResponse> findAllPatients(Integer pageNo, Integer pageSize, Boolean isActive) {
        log.info("finding all patients");
        log.info("request: pageNo: {}, pageSize: {}", pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Patient> patientPage;

        if (isActive != null && isActive)
            patientPage = this.patientRepository.findAllByIsActiveTrue(pageable);
        else
            patientPage = this.patientRepository.findAll(pageable);

        log.info("Patient fetched Successfully");

        if (patientPage.getContent().isEmpty()) {
            log.info("No Patients found");
            return PaginationResponse.makeEmptyResponse();
        }

        log.info("Total Patients found: {}", patientPage.getContent().size());

        return PaginationResponse.makeResponse(patientPage, PatientResponse::new);
    }

    @Override
    public PatientResponse createPatient(PatientCreateRequest request) {
        log.info("creating new patient");
        log.info("request patient: {}", request);

        log.info("request email validation on creation");
        if (this.alreadyAvailableEmail(request.getEmail())) {
            log.info("patient already exists against given email: {}", request.getEmail());
            throw new AlreadyExistsException("patient already exists against given email: " + request.getEmail());
        }
        log.info("requested email validation successful on creation");

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

    @Override
    public PatientResponse updatePatient(String email, PatientUpdateRequest request) {
        log.info("updating old patient");
        log.info("update request patient: {}", request);

        log.info("request email validation");
        if (this.alreadyAvailableEmail(request.getEmail())) {
            log.info("requested patient already exists against given email: {}", request.getEmail());
            throw new AlreadyExistsException("requested patient already exists against given email: " + request.getEmail());
        }
        log.info("requested email validation successful");

        log.info("validating old patient record");
        Patient founded = this.patientRepository.findPatientByEmail(email).orElseThrow(() -> {
            log.error("patient is not available against given email: {}", email);
            return new ResourceNotFoundException("patient is not available against given email: " + email);
        });

        log.info("updating new patient");
        founded.setName(request.getName());
        founded.setEmail(request.getEmail());
        founded.setAddress(request.getAddress());
        founded.setIsActive(request.getIsActive());
        founded.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        founded.setRegisteredDate(LocalDate.parse(request.getRegistrationDate()));

        Patient updated = this.patientRepository.save(founded);
        log.info("patient updated successfully");

        return new PatientResponse(updated);
    }

    private Boolean alreadyAvailableEmail(String email) {
        return this.patientRepository.findPatientByEmail(email).isPresent();
    }
}