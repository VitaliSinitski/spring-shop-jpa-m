package com.vitali.converters;

import com.vitali.dto.user.UserCreateDto;

import javax.servlet.http.HttpServletRequest;

import static com.vitali.constants.Constants.*;
import static com.vitali.util.ParameterUtil.*;

public class UserCreateConverter implements Converter<HttpServletRequest, UserCreateDto> {
    @Override
    public UserCreateDto convert(HttpServletRequest request) {
        return UserCreateDto.builder()
                .username(request.getParameter(NAME))
                .email(request.getParameter(EMAIL))
                .rawPassword(request.getParameter(PASSWORD))
                .role(getRole(request))
                .enabled(getEnabled(request))
                .firstName(request.getParameter(FIRST_NAME))
                .lastName(request.getParameter(LAST_NAME))
                .build();
    }
}
