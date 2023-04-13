package com.vitali.services;

import com.vitali.constants.Constants;
import com.vitali.entities.enums.OrderStatus;
import com.vitali.entities.Cart;
import com.vitali.entities.Order;
import com.vitali.entities.OrderItem;
import com.vitali.repositories.CartRepository;
import com.vitali.repositories.OrderItemRepository;
import com.vitali.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createNewOrder(List<Integer> ids, String information, Integer cardId) {
        Cart cart = cartRepository.findById(cardId).orElse(null);
        Order order = Order.builder()
                .inform(information)
                .cart(cart)
                .build();
        order.setOrderItems(getOrderItemsByIds(ids));
    }

    public List<OrderItem> getOrderItemsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return orderItemRepository.findAllByIdIn(ids);
    }

    public void updateOrderStatus(OrderStatus orderStatus, Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        order.setOrderStatus(Optional.ofNullable(orderStatus).orElse(Constants.DEFAULT_ORDER_STATUS));
    }


}
