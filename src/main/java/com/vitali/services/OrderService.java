package com.vitali.services;

import com.vitali.constants.Constants;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.entities.Cart;
import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.repositories.CartItemRepository;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.FilterProductRepository;
import com.vitali.database.repositories.OrderItemRepository;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.mappers.cartItem.CartItemToOrderItemMapper;
import com.vitali.mappers.order.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderReadMapper orderReadMapper;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final CartItemToOrderItemMapper cartItemToOrderItemMapper;

    @Transactional
    public void createNewOrder(List<Integer> ids, String information, Integer cardId) {
        Cart cart = cartRepository.findById(cardId).orElse(null);
        Order order = Order.builder()
                .inform(information)
                .cart(cart)
                .build();
        order.setOrderItems(getOrderItemsByIds(ids));
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
    public OrderReadDto createOrder(Integer cartId, String information) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        log.info("OrderService - createOrder - cart: {}", cart);
        // get cartItems
        List<CartItem> cartItems = cart.getCartItems();
        log.info("OrderService - createOrder - cartItems: {}", cartItems);

        // copy cartItems to orderItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
//            OrderItem orderItem = orderItemRepository.saveAndFlush(cartItemToOrderItemMapper.map(cartItem));
            OrderItem orderItem = orderItemRepository.save(cartItemToOrderItemMapper.map(cartItem));
            orderItems.add(orderItem);
        }
        log.info("OrderService - createOrder - orderItems: {}", orderItems);


        // update the product quantity
//            Product product = cartItem.getProduct();
//            Integer productId = cartItem.getProduct().getId();
//            log.info("OrderService - crateOrder - productId: {}", productId);
//            Product product = productRepository.findById(productId).orElseThrow();
//            log.info("OrderService - crateOrder - product: {}", product);
//            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
//            productRepository.save(product);

        // create Order object
        Order order = new Order();
        // set cart, information, status, OrderItems
        order.setCart(cart);
        order.setInform(information);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderItems(orderItems);
        // save Order ???
        Order savedOrder = orderRepository.save(order);

        // delete the cart items
        cartItemRepository.deleteAll(cartItems);
//        cart.setCartItems(new ArrayList<>());
        return orderReadMapper.map(savedOrder);
    }


}
