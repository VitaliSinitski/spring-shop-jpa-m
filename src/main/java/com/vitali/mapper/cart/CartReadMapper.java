package com.vitali.mapper.cart;

import com.vitali.dto.cart.CartReadDto;
import com.vitali.entity.Cart;
import com.vitali.mapper.Mapper;
import com.vitali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
