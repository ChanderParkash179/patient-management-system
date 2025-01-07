package com.pm.patientservice.service;

import com.pm.patientservice.dtos.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;

public interface PatientService {

    PaginationResponse<PatientResponse> findAllPatients(Integer pageNo, Integer pageSize);
}