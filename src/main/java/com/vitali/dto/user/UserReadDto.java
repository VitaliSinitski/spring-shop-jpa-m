package com.vitali.dto.user;

import com.vitali.constants.Role;
import com.vitali.dto.cart.CartReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
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
    LocalDate birthDate;
    CartReadDto cart;
}
