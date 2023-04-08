package com.vitali.mapper.user;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.entity.User;
import com.vitali.mapper.Mapper;
import com.vitali.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    private final CartRepository cartRepository;
    @Override
    public User map(UserCreateDto object) {
        return User.builder()
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .birthDate(object.getBirthDate())
                // либо можно пробарсывать исключение. Если передано cartId, которого не существует, то скорее всего это неверный параметр
                // поэтому мы должны это проверять на уровне валидации,
                // то есть на этом этапе не должно произойти этого исключения, поэтому это исключительный случай,
                // но это лучше проверять там, где без этого id не должно существовать и самой сущности (типа Company <-| Employee)
//                .cart(cartDao.findById(object.getCartId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
