package com.vitali.dto.order;

import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.user.UserReadDto;
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
    CartReadDto cart;
    UserReadDto user;
    List<OrderItemReadDto> orderItems;
}
