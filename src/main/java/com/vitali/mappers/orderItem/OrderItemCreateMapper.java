package com.vitali.mappers.orderItem;

import com.vitali.mappers.Mapper;
import com.vitali.repositories.CartRepository;
import com.vitali.repositories.ProductRepository;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.entities.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemCreateMapper implements Mapper<OrderItemCreateDto, OrderItem> {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    @Override
    public OrderItem map(OrderItemCreateDto object) {
        return OrderItem.builder()
                .quantity(object.getQuantity())
                .product(productRepository.findById(object.getProductId())
                        .orElseThrow(IllegalArgumentException::new))
                .cart(cartRepository.findById(object.getCartId())
                        .orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
