package com.vitali.converters;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;

import static com.vitali.constants.Constants.ADDRESS;
import static com.vitali.constants.Constants.BIRTH_DATE;
import static com.vitali.constants.Constants.EMAIL;
import static com.vitali.constants.Constants.FIRST_NAME;
import static com.vitali.constants.Constants.LAST_NAME;
import static com.vitali.constants.Constants.NAME;
import static com.vitali.constants.Constants.PASSWORD;
import static com.vitali.constants.Constants.PHONE;
import static com.vitali.util.ParameterUtil.getEnabled;
import static com.vitali.util.ParameterUtil.getRole;

@Component
public class UserInformationCreateConverter implements Converter<HttpServletRequest, UserInformationCreateDto> {
    @Override
    public UserInformationCreateDto convert(HttpServletRequest request) {
        return UserInformationCreateDto.builder()
                .firstName(request.getParameter(FIRST_NAME))
                .lastName(request.getParameter(LAST_NAME))
                .phone(request.getParameter(PHONE))
                .address(request.getParameter(ADDRESS))
                .birthDate(LocalDate.parse(request.getParameter(BIRTH_DATE)))
                .build();
    }
}
