package com.vitali.exception;

public class RegistrationPasswordNotMatchingException extends RuntimeException {

    public RegistrationPasswordNotMatchingException(String message) {
        super(message);
    }

}


//public class RegistrationPasswordNotMatchingException extends RuntimeException {
//
//    private final UserCreateDto user;
//    private final UserInformationCreateDto userInformation;
//
//    public RegistrationPasswordNotMatchingException(String message, UserCreateDto user, UserInformationCreateDto userInformation) {
//        super(message);
//    }
//}