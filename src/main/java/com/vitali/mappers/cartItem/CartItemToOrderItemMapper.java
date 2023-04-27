package com.vitali.mappers.cartItem;

import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.OrderItem;
import com.vitali.mappers.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CartItemToOrderItemMapper implements Mapper<CartItem, OrderItem> {
    @Override
    public OrderItem map(CartItem object) {
        return OrderItem.builder()
                .cart(object.getCart())
                .quantity(object.getQuantity())
                .product(object.getProduct())
                .build();
    }
}
