package com.vitali.mappers.cartItem;

import com.vitali.database.entities.CartItem;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemCreateMapper implements Mapper<CartItemCreateDto, CartItem> {
    @Override
    public CartItem map(CartItemCreateDto object) {
        return CartItem.builder()
                .quantity(object.getQuantity())
//                .product(getProduct(object.getProductId()))
//                .cart(getCart(object.getCartId()))
                .build();
    }

//    public Cart getCart(Integer id) {
//        return Optional.ofNullable(id)
//                .flatMap(cartRepository::findById)
//                .orElse(null);
//    }

//    public Product getProduct(Integer id) {
//        return Optional.ofNullable(id)
//                .flatMap(productRepository::findById)
//                .orElse(null);
//    }
}
