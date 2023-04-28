package com.vitali.dto.order;

import com.vitali.database.entities.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderCreateDto {
    Integer cartId;
    Integer userId;
    String inform;
    OrderStatus orderStatus;
}
