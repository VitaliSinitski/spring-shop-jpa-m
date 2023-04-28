package com.vitali.dto.userInformation;

import com.vitali.dto.user.UserReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class UserInformationReadDto {
    Integer id;
    String firstName;
    String lastName;
    String phone;
    String address;
    LocalDate birthDate;
    UserReadDto user;
}
