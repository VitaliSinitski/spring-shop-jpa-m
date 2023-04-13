package com.vitali.dto.order;

import com.vitali.entities.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderCreateDto {
    Integer cartId;
//    LocalDateTime createTime;
    String inform;
    OrderStatus orderStatus;
}
