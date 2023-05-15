package com.vitali.exception;

public class RegistrationPasswordNotMatchingException extends RuntimeException {

    public RegistrationPasswordNotMatchingException(String message) {
        super(message);
    }

}