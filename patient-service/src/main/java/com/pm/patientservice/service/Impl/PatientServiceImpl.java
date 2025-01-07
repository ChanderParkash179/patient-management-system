package com.pm.patientservice.service.Impl;

import com.pm.patientservice.dtos.response.PatientResponse;
import com.pm.patientservice.dtos.wrapper.PaginationResponse;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}