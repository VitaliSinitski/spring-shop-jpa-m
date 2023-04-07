package com.vitali.dto;

import com.vitali.entity.OrderItem;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Value
public class ProductReadDto {
    Integer id;
    String name;
    String description;
    BigDecimal price;
    Integer quantity;
    String image;
//    Part image;
    CategoryReadDto category;
    ProducerReadDto producer;
    List<OrderItemReadDto> orderItems;
}
