package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        log.info("consuming kafka-event in analytics-service");

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            log.info("patient event received data is => patientId: {}, patientName: {}, patientEmail: {}", patientEvent.getPatientId().toString(), patientEvent.getName(), patientEvent.getEmail());

        } catch (InvalidProtocolBufferException e) {
            log.error("error while de-serializing event: {}", e.getMessage());
        }
    }
}