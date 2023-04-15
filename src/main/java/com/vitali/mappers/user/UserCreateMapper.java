package com.vitali.mappers.user;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.entities.Cart;
import com.vitali.entities.User;
import com.vitali.mappers.Mapper;
import com.vitali.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    private final CartRepository cartRepository;
    @Override
    public User map(UserCreateDto object) {
//        Cart cart = Cart.builder().createdDate(LocalDateTime.now()).build();
        return User.builder()
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getRawPassword())
                .role(object.getRole())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .birthDate(object.getBirthDate())
                .enabled(object.isEnabled())
//                .cart(cart)
                // либо можно пробарсывать исключение. Если передано cartId, которого не существует, то скорее всего это неверный параметр
                // поэтому мы должны это проверять на уровне валидации,
                // то есть на этом этапе не должно произойти этого исключения, поэтому это исключительный случай,
                // но это лучше проверять там, где без этого id не должно существовать и самой сущности (типа Company <-| Employee)
//                .cart(cartDao.findById(object.getCartId()).orElseThrow(IllegalArgumentException::new))
                .build();

//        User userEntity = User.builder()
//                .email(userCreateDto.getEmail())
//                .password(userCreateDto.getPassword())
//                .roles(Collections.singleton(Role.USER))
//                .build();
    }
}
