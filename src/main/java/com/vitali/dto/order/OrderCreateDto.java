package com.vitali.dto.order;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderCreateDto {
    Long cartId;
//    LocalDateTime createTime;
//    String inform;
}
