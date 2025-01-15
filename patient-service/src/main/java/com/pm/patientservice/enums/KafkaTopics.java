package com.pm.patientservice.enums;

import lombok.Getter;

@Getter
public enum KafkaTopics {

    PATIENT("patient");

    private final String topic;

    KafkaTopics(String topic) {
        this.topic = topic;
    }
}