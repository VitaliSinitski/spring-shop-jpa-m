package com.vitali.dto.orderItem;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.order.OrderReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class OrderItemReadDto {
    Integer id;
    LocalDateTime createdDate;
    Integer quantity;
    ProductReadDto product;
    OrderReadDto order;
    CartReadDto cart;
//    CategoryReadDto category;
//    ProducerReadDto producer;

}
