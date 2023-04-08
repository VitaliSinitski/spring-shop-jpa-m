package com.vitali.mapper.order;

import com.vitali.mapper.Mapper;
import com.vitali.repository.CartRepository;
import com.vitali.dto.OrderCreateDto;
import com.vitali.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCreateMapper implements Mapper<OrderCreateDto, Order> {
    private final CartRepository cartRepository;
    @Override
    public Order map(OrderCreateDto object) {
        return Order.builder()
                .cart(cartRepository.findById(object.getCartId())
                        .orElseThrow(IllegalArgumentException::new))
                .build();
    }

    @Override
    public List<Order> mapList(List<OrderCreateDto> objects) {
        return null;
    }
}
