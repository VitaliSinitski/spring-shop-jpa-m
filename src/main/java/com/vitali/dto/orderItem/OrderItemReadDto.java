package com.vitali.dto.orderItem;

import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.product.ProductReadDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderItemReadDto {
    private Integer id;
    private LocalDateTime createdDate;
    private Integer quantity;
    private ProductReadDto product;
    private OrderReadDto order;
    private CartReadDto cart;

}
