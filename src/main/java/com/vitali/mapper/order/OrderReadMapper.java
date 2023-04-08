package com.vitali.mapper.order;

import com.vitali.dto.OrderReadDto;
import com.vitali.entity.Order;
import com.vitali.mapper.Mapper;

import java.util.List;

public class OrderReadMapper implements Mapper<Order, OrderReadDto> {
    @Override
    public OrderReadDto map(Order object) {
        return OrderReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreateTime())
                .updatedDate(object.getCreateTime())
                .orderStatus()
                .orderItems()
                .build();
    }

    @Override
    public List<OrderReadDto> mapList(List<Order> objects) {
        return null;
    }
}
