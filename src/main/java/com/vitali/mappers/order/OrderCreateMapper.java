package com.vitali.mappers.order;

import com.vitali.mappers.Mapper;
import com.vitali.repositories.CartRepository;
import com.vitali.dto.order.OrderCreateDto;
import com.vitali.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.vitali.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class OrderCreateMapper implements Mapper<OrderCreateDto, Order> {
    private final CartRepository cartRepository;
    @Override
    public Order map(OrderCreateDto object) {
        return Order.builder()
                .cart(cartRepository.findById(object.getCartId())
                        .orElseThrow(IllegalArgumentException::new))
                .orderStatus(Optional.ofNullable(object.getOrderStatus()).orElse(DEFAULT_ORDER_STATUS))
                .inform(object.getInform())
                .build();
    }
}
