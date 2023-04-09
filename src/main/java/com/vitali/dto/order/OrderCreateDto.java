package com.vitali.dto.order;

import com.vitali.constants.OrderStatus;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderCreateDto {
    Long cartId;
//    LocalDateTime createTime;
    String inform;
    OrderStatus orderStatus;
}
