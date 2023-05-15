package com.vitali.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilterDto {
    String username;
    String email;
}
