package com.pm.patientservice.dtos.patient.response;

import lombok.*;
import com.pm.patientservice.model.Patient;

import java.util.UUID;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private UUID id;
    private String name;
    private String email;
    private Boolean isActive;
    private String address;
    private LocalDate dateOfBirth;

    public PatientResponse(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.isActive = patient.getIsActive();
        this.address = patient.getAddress();
        this.dateOfBirth = patient.getDateOfBirth();
    }
}