package com.vitali.services;

import com.vitali.database.entities.*;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.repositories.*;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.exception.OutOfStockException;
import com.vitali.mappers.cartItem.CartItemToOrderItemMapper;
import com.vitali.mappers.order.OrderCreateMapper;
import com.vitali.mappers.order.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderReadMapper orderReadMapper;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartItemToOrderItemMapper cartItemToOrderItemMapper;


    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public void updateOrderStatus(OrderStatus orderStatus, Integer orderId) {
        orderRepository.findById(orderId)
                .map(entity -> {
                    entity.setOrderStatus(orderStatus);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
    }

    @Transactional
    public OrderReadDto createOrder(Integer userId, Integer cartId, String information) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<CartItem> cartItems = cart.getCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemRepository.save(cartItemToOrderItemMapper.map(cartItem));
            orderItems.add(orderItem);
            boolean stockConfirmation = productService.updateProductQuantityByCartItem(cartItem);
            if (!stockConfirmation) {
                throw new OutOfStockException(cartItem.getProduct().getName());
            }
        }

        Order order = new Order();
        order.setCart(cart);
        order.setUser(user);
        order.setInform(information);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartItemService.deleteAllByCartId(cartId);
        return orderReadMapper.map(savedOrder);
    }
}
