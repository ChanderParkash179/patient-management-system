package com.pm.patientservice.kafka;

import com.pm.patientservice.enums.EventType;
import com.pm.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType(EventType.PATIENT_CREATED.getEventType())
                .build();

        try {
            this.kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("error sending PatientCreated event: {}", event);
        }
    }
}