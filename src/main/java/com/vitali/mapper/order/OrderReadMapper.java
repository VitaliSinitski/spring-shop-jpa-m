package com.vitali.mapper.order;

import com.vitali.dto.order.OrderReadDto;
import com.vitali.entity.Order;
import com.vitali.mapper.Mapper;
import com.vitali.mapper.orderItem.OrderItemReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {   // Done
    private final OrderItemReadMapper orderItemReadMapper;
    @Override
    public OrderReadDto map(Order object) {
        return OrderReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreatedDate())
                .updatedDate(object.getUpdatedDate())
                .orderStatus(object.getOrderStatus())
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
