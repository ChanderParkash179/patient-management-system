package com.pm.patientservice.enums;

import lombok.Getter;

@Getter
public enum EventType {
    PATIENT_CREATED("PATIENT_CREATED");

    private final String eventType;

    EventType(String eventType) {
        this.eventType = eventType;
    }
}
