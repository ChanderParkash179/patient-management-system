package com.pm.patientservice.controller;

import com.pm.patientservice.dtos.patient.request.PatientCreateRequest;
import com.pm.patientservice.dtos.patient.request.PatientUpdateRequest;
import com.pm.patientservice.dtos.patient.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.ApiResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<PatientResponse>>> findAllPatients(
            @RequestParam(required = false) boolean isActive,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NO) Integer pageNo,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize) {

        PaginationResponse<PatientResponse> response = this.patientService.findAllPatients(pageNo, pageSize, isActive);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patients founded successfully!", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @RequestBody PatientCreateRequest request) {

        PatientResponse response = this.patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(HttpStatus.CREATED.value(), "patient created successfully!", response));
    }

    @PutMapping("{patient-email}/patient")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(@PathVariable("patient-email") String email, @Valid @RequestBody PatientUpdateRequest request) {

        PatientResponse response = this.patientService.updatePatient(email, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patient updated successfully!", response));
    }
}