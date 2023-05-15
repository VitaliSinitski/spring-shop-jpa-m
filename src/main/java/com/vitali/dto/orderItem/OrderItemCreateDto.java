package com.vitali.dto.orderItem;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderItemCreateDto {
    private Integer quantity;
    private Integer productId;
    private Integer cartId;

}
