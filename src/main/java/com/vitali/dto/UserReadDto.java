package com.vitali.dto;

import com.vitali.constants.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class UserReadDto {
    Long id;
    String name;
    String email;
    String password;
    Role role;
    String firstName;
    String lastName;
    LocalDateTime birthDate;
    CartReadDto cart;
}
