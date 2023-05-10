package com.vitali.dto.user;

import com.vitali.database.entities.enums.Role;
import com.vitali.validation.PasswordNotBlankAndSize;
import com.vitali.validation.UserInfo;
import com.vitali.validation.group.CreateAction;
import com.vitali.validation.group.UpdateValidationGroup;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Value
public class UserCreateDto {
    @Size(min = 2, max = 64, message = "Username length must be between 2 and 20")
    @NotBlank(message = "Username must not be empty")
    String username;
    @Email(message = "Email format is incorrect")
    @NotBlank(message = "Email must not be empty")
    String email;
    @PasswordNotBlankAndSize(checkOnCreate = false, groups = {UpdateValidationGroup.class})
    String rawPassword;
    String matchingPassword;
    Role role;
    Boolean enabled;
    Integer cartId;
    Integer userInformationId;
}
