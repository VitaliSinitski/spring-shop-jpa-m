package com.vitali.dto.user;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilterDto {
    String name;
//    LocalDate birthdate;
    String email;
}
