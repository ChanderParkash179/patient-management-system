package com.pm.patientservice.dtos.billing;

import com.pm.patientservice.model.Patient;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatientBillingRequest {

    private String id;
    private String name;
    private String email;

    public PatientBillingRequest(Patient patient){
        this.id = patient.getId().toString();
        this.name = patient.getName();
        this.email = patient.getEmail();
    }

    @Override
    public String toString() {
        return "PatientBillingRequest {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}