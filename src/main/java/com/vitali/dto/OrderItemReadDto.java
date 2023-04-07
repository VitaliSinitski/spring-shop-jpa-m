package com.vitali.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class OrderItemReadDto {
    Integer id;
    LocalDateTime createTime;
    Integer quantity;
    ProductReadDto product;
    OrderReadDto order;
    CartReadDto cart;
//    CategoryReadDto category;
//    ProducerReadDto producer;

}
