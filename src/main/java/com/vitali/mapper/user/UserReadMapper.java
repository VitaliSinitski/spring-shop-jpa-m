package com.vitali.mapper.user;

import com.vitali.dto.user.UserReadDto;
import com.vitali.entity.User;
import com.vitali.mapper.Mapper;
import com.vitali.mapper.cart.CartReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    private final CartReadMapper cartReadMapper;
    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .birthDate(object.getBirthDate())
                .cart(Optional.ofNullable(object.getCart())
                        .map(cartReadMapper::map).orElse(null))
                .build();
    }
}
