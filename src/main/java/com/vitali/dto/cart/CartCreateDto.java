package com.vitali.dto.cart;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
@Builder
@Value
public class CartCreateDto {
    LocalDateTime createTime;
}
