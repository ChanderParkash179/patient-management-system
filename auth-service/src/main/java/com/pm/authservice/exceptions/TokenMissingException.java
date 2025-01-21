package com.pm.authservice.exceptions;

public class TokenMissingException extends RuntimeException {

    public TokenMissingException(String message){
        super(message);
    }
}
