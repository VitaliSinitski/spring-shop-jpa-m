package com.vitali.dto.cartItem;

import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.product.ProductReadDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
