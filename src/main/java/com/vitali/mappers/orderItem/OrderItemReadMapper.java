package com.vitali.mappers.orderItem;

import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.entities.OrderItem;
import com.vitali.mappers.Mapper;
import com.vitali.mappers.cart.CartReadMapper;
import com.vitali.mappers.product.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemReadMapper implements Mapper<OrderItem, OrderItemReadDto> {       // Almost done (order ??)
    private final ProductReadMapper productReadMapper;
    private final CartReadMapper cartReadMapper;
    @Override
    public OrderItemReadDto map(OrderItem object) {
        return OrderItemReadDto.builder()
                .id(object.getId())
                .createdDate(object.getCreatedDate())
                .quantity(object.getQuantity())
                .product(Optional.ofNullable(object.getProduct())
                        .map(productReadMapper::map).orElse(null))
//                .order(Optional.ofNullable(object.getOrder())                 // ???
//                        .map(orderReadMapper::mapFrom).orElse(null))
                .cart(Optional.ofNullable(object.getCart())
                        .map(cartReadMapper::map).orElse(null))
                .build();
    }

    public List<OrderItemReadDto> mapList(List<OrderItem> objects) {
        if (objects == null || objects.isEmpty()) {
            return Collections.emptyList();
        }
        return objects
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
