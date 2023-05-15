package com.vitali.exception;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;

public class UserAlreadyExistsException extends RuntimeException {

    private UserCreateDto userCreateDto;
    private UserInformationCreateDto userInformationCreateDto;

    public UserAlreadyExistsException(String message, UserCreateDto userCreateDto, UserInformationCreateDto userInformationCreateDto) {
        super(message);
        this.userCreateDto = userCreateDto;
        this.userInformationCreateDto = userInformationCreateDto;
    }

    public UserCreateDto getUserCreateDto() {
        return userCreateDto;
    }

    public UserInformationCreateDto getUserInformationCreateDto() {
        return userInformationCreateDto;
    }


}