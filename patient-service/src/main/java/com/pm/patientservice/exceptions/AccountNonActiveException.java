package com.pm.patientservice.exceptions;

public class AccountNonActiveException extends RuntimeException{

    public AccountNonActiveException(String message) {
        super(message);
    }

    public AccountNonActiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
