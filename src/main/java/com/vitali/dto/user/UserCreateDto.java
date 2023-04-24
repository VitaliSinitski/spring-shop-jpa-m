package com.vitali.dto.user;

import com.vitali.database.entities.enums.Role;
import com.vitali.validation.UserInfo;
import com.vitali.validation.group.CreateAction;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Value
@UserInfo(groups = CreateAction.class)
public class UserCreateDto {
    @Size(min = 2, max = 64)
    String username;
    @Email
    String email;
    @NotBlank(groups = CreateAction.class)
    String rawPassword;
    Role role; // String role?
    Boolean enabled;
    Integer cartId; // = user.id
    Integer userInformationId;
}
