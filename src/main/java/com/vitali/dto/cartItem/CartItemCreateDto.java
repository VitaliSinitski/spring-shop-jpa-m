package com.vitali.dto.cartItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CartItemCreateDto {
    private Integer quantity;
    private Integer productId;
    private Integer cartId;

}
