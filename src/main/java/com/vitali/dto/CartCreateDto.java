package com.vitali.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
@Builder
@Value
public class CartCreateDto {
    LocalDateTime createTime;
}
