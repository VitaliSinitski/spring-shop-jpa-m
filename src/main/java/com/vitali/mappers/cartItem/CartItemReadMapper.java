package com.vitali.mappers.cartItem;

import com.vitali.database.entities.CartItem;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.mappers.Mapper;
import com.vitali.mappers.product.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemReadMapper implements Mapper<CartItem, CartItemReadDto> {       // Almost done (order ??)
    private final ProductReadMapper productReadMapper;
    @Override
    public CartItemReadDto map(CartItem object) {
        return CartItemReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreatedDate())
                .quantity(object.getQuantity())
                .product(Optional.ofNullable(object.getProduct())
                        .map(productReadMapper::map).orElse(null))
                .build();
    }
}
