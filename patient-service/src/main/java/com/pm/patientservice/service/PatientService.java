package com.pm.patientservice.service;

import com.pm.patientservice.dtos.patient.request.PatientCreateRequest;
import com.pm.patientservice.dtos.patient.request.PatientUpdateRequest;
import com.pm.patientservice.dtos.patient.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import jakarta.validation.Valid;

public interface PatientService {

    PaginationResponse<PatientResponse> findAllPatients(Integer pageNo, Integer pageSize, Boolean isActive);

    PatientResponse createPatient(PatientCreateRequest request);

    PatientResponse updatePatient(String email, @Valid PatientUpdateRequest request);

    void deletePatient(String email);

    PatientResponse findPatientByEmail(String email);
}