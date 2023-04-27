package com.vitali.mappers.cartItem;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.Product;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemCreateMapper implements Mapper<CartItemCreateDto, CartItem> {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    @Override
    public CartItem map(CartItemCreateDto object) {
        return CartItem.builder()
                .quantity(object.getQuantity())
                .product(getProduct(object.getProductId()))
                .cart(getCart(object.getCartId()))
                .build();
    }

    public Cart getCart(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(cartRepository::findById)
                .orElse(null);
    }

    public Product getProduct(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(productRepository::findById)
                .orElse(null);
    }
}
