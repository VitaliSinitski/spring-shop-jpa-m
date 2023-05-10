package com.vitali.mappers.userInformation;

import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInformationCreateMapper implements Mapper<UserInformationCreateDto, UserInformation> {
    private final UserRepository userRepository;
    @Override
    public UserInformation map(UserInformationCreateDto userInformationCreateDto) {
        log.info("UserInformationCreateMapper - map - userInformationCreateDto: {}", userInformationCreateDto);
        UserInformation userInformation = new UserInformation();
        copy(userInformationCreateDto, userInformation);
        return userInformation;
    }

    @Override
    public UserInformation map(UserInformationCreateDto fromObject, UserInformation userInformation) {
        log.info("UserInformationCreateMapper - map - userInformationCreateDto: {}", fromObject);
        copy(fromObject, userInformation);
        return userInformation;
    }

    private void copy(UserInformationCreateDto object, UserInformation userInformation) {
        userInformation.setFirstName(object.getFirstName());
        userInformation.setLastName(object.getLastName());
        userInformation.setPhone(object.getPhone());
        userInformation.setAddress(object.getAddress());
        userInformation.setBirthDate(object.getBirthDate());
//        userInformation.setUser(getUser(object.getUserId()));
    }

    public User getUser(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
