package com.vitali.mappers.userInformation;

import com.vitali.database.entities.UserInformation;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInformationReadMapper implements Mapper<UserInformation, UserInformationReadDto> {
    @Override
    public UserInformationReadDto map(UserInformation object) {
        return UserInformationReadDto.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .phone(object.getPhone())
                .address(object.getAddress())
                .birthDate(object.getBirthDate())
                .build();
    }
}
