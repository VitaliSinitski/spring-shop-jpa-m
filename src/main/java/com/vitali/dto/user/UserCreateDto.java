package com.vitali.dto.user;

import com.vitali.entities.enums.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class UserCreateDto {
    String username;
    String email;
    Integer cartId; // = user.id
    String password;
    Role role; // String role?
    boolean enabled;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
