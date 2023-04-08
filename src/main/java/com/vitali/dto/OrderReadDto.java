package com.vitali.dto;

import com.vitali.constants.OrderStatus;
import com.vitali.entity.Order;
import com.vitali.entity.OrderItem;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Value
public class OrderReadDto {
    Integer id;
//    String inform;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    OrderStatus orderStatus;
    List<OrderItemReadDto> orderItems;
}
