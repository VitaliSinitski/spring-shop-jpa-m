package com.vitali.dto;

import com.vitali.entity.Order;
import com.vitali.entity.OrderItem;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class CartReadDto {
    Long id;
    LocalDateTime createTime;
    UserReadDto user;
    List<OrderReadDto> orders;
    List<OrderItemReadDto> orderItems;
}
