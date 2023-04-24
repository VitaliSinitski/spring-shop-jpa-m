package com.vitali.dto.userInformation;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
public class UserInformationCreateDto {
    @Size(min = 2, max = 64)
    String firstName;
    @Size(min = 2, max = 64)
    String lastName;
    String phone;
    String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
}
