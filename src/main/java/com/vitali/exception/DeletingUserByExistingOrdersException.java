package com.vitali.exception;

public class DeletingUserByExistingOrdersException extends RuntimeException{

    public DeletingUserByExistingOrdersException(String message) {
        super(message);
    }

}