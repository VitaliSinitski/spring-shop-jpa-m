package com.vitali.services;

import com.vitali.constants.Constants;
import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.User;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.repositories.CartItemRepository;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.OrderItemRepository;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.dto.cart.CartReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.mappers.cart.CartReadMapper;
import com.vitali.mappers.cartItem.CartItemToOrderItemMapper;
import com.vitali.mappers.order.OrderReadMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final CartReadMapper cartReadMapper;
    private final OrderReadMapper orderReadMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemReadMapper orderItemReadMapper;
    private final OrderItemService orderItemService;
    private final CartItemToOrderItemMapper cartItemToOrderItemMapper;
    private final CartItemRepository cartItemRepository;


    @Transactional
    public OrderReadDto createOrder(Integer cartId, String inform, List<Integer> cartItemsIds) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        log.info("CartService - cart: {}", cart);
        List<CartItem> cartItems = new ArrayList<>();
        for (Integer itemId : cartItemsIds) {
            CartItem item = cartItemRepository.findById(itemId).orElse(null);
            cartItems.add(item);
        }
        log.info("CartService - cartItems: {}", cartItems);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemRepository.saveAndFlush(cartItemToOrderItemMapper.map(cartItem));
//            OrderItem orderItem = orderItemRepository.save(cartItemToOrderItemMapper.map(cartItem));
            orderItems.add(orderItem);
        }
        log.info("CartService - orderItems: {}", orderItems);

        Order order = Order.builder()
                .cart(cart)
//                .inform(inform)
                .orderStatus(Constants.DEFAULT_ORDER_STATUS)
                .orderItems(orderItems)
                .build();
        Order savedOrder = orderRepository.save(order);

        cartItemService.deleteAllByCartId(cartId);
        return orderReadMapper.map(savedOrder);
    }

}
