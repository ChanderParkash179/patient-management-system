package com.pm.patientservice.controller;

import com.pm.patientservice.dtos.patient.request.PatientCreateRequest;
import com.pm.patientservice.dtos.patient.request.PatientUpdateRequest;
import com.pm.patientservice.dtos.patient.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.ApiResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
@Tag(name = "Patient", description = "api service for managing patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "get all patients")
    public ResponseEntity<ApiResponse<PaginationResponse<PatientResponse>>> findAllPatients(
            @RequestParam(required = false) boolean isActive,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NO) Integer pageNo,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize) {

        PaginationResponse<PatientResponse> response = this.patientService.findAllPatients(pageNo, pageSize, isActive);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patients founded successfully!", response));
    }

    @PostMapping
    @Operation(summary = "create a new patient")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @RequestBody PatientCreateRequest request) {

        PatientResponse response = this.patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(HttpStatus.CREATED.value(), "patient created successfully!", response));
    }

    @GetMapping("{patient-email}/patient")
    @Operation(summary = "get a patient by email address")
    public ResponseEntity<ApiResponse<PatientResponse>> findPatientByEmail(@PathVariable("patient-email") String email) {

        PatientResponse response = this.patientService.findPatientByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patient founded successfully!", response));
    }

    @PutMapping("{patient-email}/patient")
    @Operation(summary = "update an old patient data")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(@PathVariable("patient-email") String email, @Valid @RequestBody PatientUpdateRequest request) {

        PatientResponse response = this.patientService.updatePatient(email, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patient updated successfully!", response));
    }

    @DeleteMapping("{patient-email}/patient")
    @Operation(summary = "delete patient record")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable("patient-email") String email) {

        this.patientService.deletePatient(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patient deleted successfully against given email: " + email));
    }
}