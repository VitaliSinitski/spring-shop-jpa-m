package com.vitali.mappers.cart;

import com.vitali.dto.cart.CartReadDto;
import com.vitali.database.entities.Cart;
import com.vitali.mappers.Mapper;
import com.vitali.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements Mapper<Cart, CartReadDto> {
    private final UserRepository userRepository;
    @Override
    public CartReadDto map(Cart object) {
        return CartReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreatedDate())
                .build();
    }
}
