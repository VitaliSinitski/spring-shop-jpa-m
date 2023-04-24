package com.vitali.mappers.user;

import com.vitali.dto.user.UserReadDto;
import com.vitali.database.entities.User;
import com.vitali.mappers.Mapper;
import com.vitali.mappers.cart.CartReadMapper;
import com.vitali.mappers.userInformation.UserInformationReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    private final CartReadMapper cartReadMapper;
    private final UserInformationReadMapper userInformationReadMapper;
    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .enabled(object.isEnabled())
                .cart(Optional.ofNullable(object.getCart())
                        .map(cartReadMapper::map).orElse(null))
                .userInformation(Optional.ofNullable(object.getUserInformation())
                        .map(userInformationReadMapper::map).orElse(null))
                .build();
    }
}
