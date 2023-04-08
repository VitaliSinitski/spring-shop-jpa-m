package com.vitali.mapper.orderItem;

import com.vitali.mapper.Mapper;
import com.vitali.repository.CartRepository;
import com.vitali.repository.ProductRepository;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
