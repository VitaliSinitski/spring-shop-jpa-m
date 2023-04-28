package com.vitali.mappers.order;

import com.vitali.dto.order.OrderReadDto;
import com.vitali.database.entities.Order;
import com.vitali.mappers.Mapper;
import com.vitali.mappers.cart.CartReadMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import com.vitali.mappers.user.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {   // Done
    private final OrderItemReadMapper orderItemReadMapper;
    private final CartReadMapper cartReadMapper;
    private final UserReadMapper userReadMapper;
    @Override
    public OrderReadDto map(Order object) {
        log.info("OrderReadMapper - map - start");
        return OrderReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreatedDate())
                .updatedDate(object.getUpdatedDate())
                .orderStatus(object.getOrderStatus())
                .inform(object.getInform())
                .cart(cartReadMapper.map(object.getCart()))
                .user(userReadMapper.map(object.getUser()))
                .orderItems(orderItemReadMapper.mapList(object.getOrderItems()))
                .build();
    }

    public List<OrderReadDto> mapList(List<Order> objects) {
        if (objects == null || objects.isEmpty()) {
            return Collections.emptyList();
        }
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
