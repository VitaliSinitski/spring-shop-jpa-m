package com.vitali.exception;

public class DeletingUserByExistingCartItemsException extends RuntimeException{

    public DeletingUserByExistingCartItemsException(String message) {
        super(message);
    }

}