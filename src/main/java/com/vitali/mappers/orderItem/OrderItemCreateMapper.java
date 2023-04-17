package com.vitali.mappers.orderItem;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.Product;
import com.vitali.mappers.Mapper;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.database.entities.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderItemCreateMapper implements Mapper<OrderItemCreateDto, OrderItem> {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    @Override
    public OrderItem map(OrderItemCreateDto object) {
        return OrderItem.builder()
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
