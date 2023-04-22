package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.OrderItemRepository;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.mappers.cart.CartReadMapper;
import com.vitali.mappers.order.OrderReadMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemReadMapper orderItemReadMapper;

//    public Optional<OrderItem> getOrderItem(Integer id) {
//       return orderItemRepository.findById(id);
//    }

    @Transactional
    public void createOrder(Integer cartId, String inform, OrderStatus orderStatus, List<Integer> orderItemsIds) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        List<OrderItem> orderItems = new ArrayList<>();
        for (Integer itemId : orderItemsIds) {
            OrderItem item = orderItemRepository.findById(itemId).orElse(null);
            orderItems.add(item);
        }

        Order order = Order.builder()
                .cart(cart)
                .inform(inform)
                .orderStatus(orderStatus)
                .orderItems(orderItems)
                .build();
        orderRepository.save(order);
    }
}
