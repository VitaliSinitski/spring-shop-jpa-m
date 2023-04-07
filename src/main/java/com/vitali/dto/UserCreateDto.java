package com.vitali.dto;

import com.vitali.constants.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class UserCreateDto {
    String name;
    String email;
    Long cartId; // = user.id
    String password;
    Role role; // String role?
    String firstName;
    String lastName;
    LocalDateTime birthDate;
}
