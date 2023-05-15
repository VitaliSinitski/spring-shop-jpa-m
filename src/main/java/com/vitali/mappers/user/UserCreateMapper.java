package com.vitali.mappers.user;

import com.vitali.database.entities.User;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    public User mapPassword(UserCreateDto fromObject, User user) {
        Optional.ofNullable(fromObject.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
        return user;
    }

    @Override
    public User map(UserCreateDto userCreateDto) {
        User user = new User();
        copy(userCreateDto, user);
        return user;
    }

    private void copy(UserCreateDto object, User user) {

        user.setUsername(object.getUsername());
        user.setEmail(object.getEmail());
        user.setRole(object.getRole());
        user.setEnabled(object.getEnabled());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}
