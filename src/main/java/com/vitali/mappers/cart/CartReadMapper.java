package com.vitali.mappers.cart;

import com.vitali.database.entities.Cart;
import com.vitali.dto.cart.CartReadDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements Mapper<Cart, CartReadDto> {
    @Override
    public CartReadDto map(Cart object) {
        return CartReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreatedDate())
                .build();
    }
}
