package com.vitali.dto.cartItem;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CartItemCreateDto {
    private Integer quantity;
    private Integer productId;
    private Integer cartId;

}
