package com.vitali.dto.user;

import com.vitali.entities.enums.Role;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Value
public class UserCreateDto {
//    @NotNull
//    @Size(min = 2, max = 32)
    String username;
//    @Email
    String email;
    Integer cartId; // = user.id
//    @NotBlank
    String rawPassword;
    Role role; // String role?
    Boolean enabled;
//    @NotNull
//    @Size(min = 2, max = 32)
    String firstName;
//    @NotNull
//    @Size(min = 2, max = 32)
    String lastName;
//    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
}
