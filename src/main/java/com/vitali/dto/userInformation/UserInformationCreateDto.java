package com.vitali.dto.userInformation;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number. Phone number should have only digits, and start with '+'")
    @NotBlank
    String phone;
    String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthdate must not be empty")
    @Past(message = "Birthdate must be past date")
    LocalDate birthDate;
    Integer userId;
}
