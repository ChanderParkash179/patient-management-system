package com.pm.patientservice.dtos.patient.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequest {

    @NotBlank(message = "patient name can't be empty")
    @Size(max = 100, message = "patient name's length can't exceed 100 characters")
    private String name;

    @NotBlank(message = "patient email can't be empty")
    @Email(message = "patient email should be valid")
    private String email;

    private String address;

    private Boolean isActive;

    @NotBlank(message = "patient date of birth can't be empty")
    private String dateOfBirth;

    @NotBlank(message = "patient registration date can't be empty")
    private String registrationDate;

    @Override
    public String toString() {
        return "PatientUpdateRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", isActive='" + isActive + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}