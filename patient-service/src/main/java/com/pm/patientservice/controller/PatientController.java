package com.pm.patientservice.controller;

import com.pm.patientservice.dtos.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.ApiResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<PatientResponse>>> findAllPatients(
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NO) Integer pageNo,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize) {

        PaginationResponse<PatientResponse> response = this.patientService.findAllPatients(pageNo, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(HttpStatus.OK.value(), "patients founded successfully!", response));
    }
}