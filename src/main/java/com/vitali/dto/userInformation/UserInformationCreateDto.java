package com.vitali.dto.userInformation;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
public class UserInformationCreateDto {
    @Size(min = 2, max = 64, message = "Firstname length must be between 2 and 20")
    @NotBlank(message = "Firstname must not be empty")
    String firstName;
    @Size(min = 2, max = 64, message = "Lastname length must be between 2 and 20")
    @NotBlank(message = "Lastname must not be empty")
    String lastName;
    String phone;
    String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthdate must not be empty")
    LocalDate birthDate;
    Integer userId;
}
