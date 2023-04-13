package com.vitali.dto.cart;

import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.user.UserReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class CartReadDto {
    Integer id;
    LocalDateTime createdDate;
//    UserReadDto user;
//    List<OrderReadDto> orders;
//    List<OrderItemReadDto> orderItems;
}
