package com.vitali.dto.order;

import com.vitali.entities.enums.OrderStatus;
import com.vitali.dto.orderItem.OrderItemReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class OrderReadDto {
    Integer id;
    String inform;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    OrderStatus orderStatus;
    List<OrderItemReadDto> orderItems;
}
