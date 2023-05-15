package com.vitali.mappers.cartItem;

import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.OrderItem;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.mappers.Mapper;
import com.vitali.mappers.cart.CartReadMapper;
import com.vitali.mappers.product.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartItemReadMapper implements Mapper<CartItem, CartItemReadDto> {       // Almost done (order ??)
    private final ProductReadMapper productReadMapper;
    private final CartReadMapper cartReadMapper;
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
