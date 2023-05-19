package com.vitali.dto.cartItem;

import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.product.ProductReadDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CartItemReadDto {
    private Integer id;
    private LocalDateTime createdDate;
    private Integer quantity;
    private ProductReadDto product;
    private CartReadDto cart;
}
