package com.vitali.dto.user;

import com.vitali.database.entities.enums.Role;
import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserReadDto {
    Integer id;
    String username;
    String email;
    String password;
    Role role;
    boolean enabled;
    CartReadDto cart;
    UserInformationReadDto userInformation;
}
