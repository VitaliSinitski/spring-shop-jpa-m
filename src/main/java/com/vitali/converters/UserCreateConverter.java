package com.vitali.converters;

import com.vitali.dto.user.UserCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import static com.vitali.constants.Constants.*;
import static com.vitali.util.ParameterUtil.*;

@Component
public class UserCreateConverter implements Converter<HttpServletRequest, UserCreateDto> {
    @Override
    public UserCreateDto convert(HttpServletRequest request) {
        return UserCreateDto.builder()
                .username(request.getParameter(USERNAME))
                .email(request.getParameter(EMAIL))
                .rawPassword(request.getParameter(PASSWORD))
                .role(getRole(request))
                .enabled(getEnabled(request))
                .build();
    }
}
