package com.vitali.dto.userInformation;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class UserInformationReadDto {
    String firstName;
    String lastName;
    String phone;
    String address;
    LocalDate birthDate;
}
