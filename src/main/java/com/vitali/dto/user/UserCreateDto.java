package com.vitali.dto.user;

import com.vitali.database.entities.enums.Role;
import com.vitali.validation.UserInfo;
import com.vitali.validation.group.CreateAction;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Value
@UserInfo(groups = CreateAction.class)
public class UserCreateDto {
    @Size(min = 2, max = 64, message = "Username length must be between 2 and 20")
    @NotBlank(message = "Username must not be empty")
    String username;
    @Email(message = "Email format is incorrect")
    @NotBlank(message = "Email must not be empty")
    String email;
    @NotBlank(groups = CreateAction.class)
    @NotBlank(message = "Password must not be empty")
    @Size(min = 2, max = 20, message = "Password length must be between 2 and 20")
    String rawPassword;
    Role role;
    Boolean enabled;
    Integer cartId;
    Integer userInformationId;
}
