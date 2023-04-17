package com.vitali.dto.user;

import com.vitali.database.entities.enums.Role;
import com.vitali.dto.cart.CartReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class UserReadDto {
    Integer id;
    String username;
    String email;
    String password;
    Role role;
    boolean enabled;
    String firstName;
    String lastName;
    LocalDate birthDate;
    CartReadDto cart;
}
