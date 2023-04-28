package com.vitali.services;

import com.vitali.constants.Constants;
import com.vitali.database.entities.*;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.repositories.*;
import com.vitali.dto.order.OrderCreateDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductReadDto;
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
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateMapper orderCreateMapper;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;
    private final CartItemToOrderItemMapper cartItemToOrderItemMapper;

//    @Transactional
//    public void createNewOrder(List<Integer> ids, String information, Integer cardId) {
//        Cart cart = cartRepository.findById(cardId).orElse(null);
//        Order order = Order.builder()
//                .inform(information)
//                .cart(cart)
//                .build();
//        order.setOrderItems(getOrderItemsByIds(ids));
//    }

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    public List<OrderItem> getOrderItemsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return orderItemRepository.findAllByIdIn(ids);
    }

    @Transactional
    public void updateOrderStatus(OrderStatus orderStatus, Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        order.setOrderStatus(Optional.ofNullable(orderStatus).orElse(Constants.DEFAULT_ORDER_STATUS));
    }

    @Transactional
    public OrderReadDto createOrder(Integer userId, Integer cartId, String information) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        // get cartItems
        List<CartItem> cartItems = cart.getCartItems();

        // copy cartItems to orderItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemRepository.save(cartItemToOrderItemMapper.map(cartItem));
            orderItems.add(orderItem);

            // update the product quantity
            productService.updateProductQuantityByCartItem(cartItem);

        }
        // create Order object
        Order order = new Order();

        // set cart, information, status, OrderItems
        order.setCart(cart);
        order.setUser(user);
        order.setInform(information);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderItems(orderItems);

        // save Order
        Order savedOrder = orderRepository.save(order);

        // delete the cart items
        cartItemService.deleteAllByCartId(cartId);
        return orderReadMapper.map(savedOrder);
    }



    @Transactional
    public Optional<Object> update(Integer id, OrderCreateDto orderCreateDto) {
        return orderRepository.findById(id)
                .map(order -> orderCreateMapper.map(orderCreateDto, order))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }
}
