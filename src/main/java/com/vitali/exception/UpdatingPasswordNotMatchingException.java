package com.vitali.exception;

public class UpdatingPasswordNotMatchingException extends RuntimeException {

    public UpdatingPasswordNotMatchingException(String message) {
        super(message);
    }

}