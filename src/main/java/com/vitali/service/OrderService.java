package com.vitali.service;

import com.vitali.constants.Constants;
import com.vitali.constants.OrderStatus;
import com.vitali.entity.Cart;
import com.vitali.entity.Order;
import com.vitali.entity.OrderItem;
import com.vitali.mapper.order.OrderCreateMapper;
import com.vitali.mapper.order.OrderReadMapper;
import com.vitali.repository.CartRepository;
import com.vitali.repository.OrderItemRepository;
import com.vitali.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createNewOrder(List<Integer> ids, String information, Long cardId) {
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
