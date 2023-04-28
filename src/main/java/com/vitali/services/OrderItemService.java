package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.repositories.CartRepository;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.database.entities.OrderItem;
import com.vitali.mappers.orderItem.OrderItemCreateMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import com.vitali.database.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemReadMapper orderItemReadMapper;
    private final OrderItemCreateMapper orderItemCreateMapper;
    private final CartRepository cartRepository;

//    @Transactional
//    public Integer create(OrderItemCreateDto orderItemCreateDto) {
//        OrderItem orderItemEntity = orderItemCreateMapper.map(orderItemCreateDto);
//        return orderItemRepository.save(orderItemEntity).getId();
//    }


    // I use this in OrderService createOrder()
//    @Transactional
//    public Integer create(Integer quantity, Integer productId, Integer cartId) {
//        OrderItemCreateDto orderItemCreateDto = OrderItemCreateDto.builder()
//                .quantity(quantity)
//                .productId(productId)
//                .cartId(cartId)
//                .build();
//        OrderItem orderItemEntity = orderItemCreateMapper.map(orderItemCreateDto);
//        return orderItemRepository.save(orderItemEntity).getId();
//    }

    public Optional<OrderItemReadDto> findById(Integer id) {
        return orderItemRepository.findById(id)
                .map(orderItemReadMapper::map);
    }

//    public List<OrderItemReadDto> findAllByCartId(Integer id) {
//        return orderItemRepository.findOrderItemsByCartId(id)
//                .stream()
//                .map(orderItemReadMapper::map)
//                .collect(Collectors.toList());
//    }

    public boolean findOrderItemByProductId(Integer id) {
        return orderItemRepository.existsOrderItemByProductId(id);
    }

    public List<OrderItemReadDto> findAllByOrderId(Integer id) {
        return orderItemRepository.findOrderItemsByOrderId(id).stream()
                .map(orderItemReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Integer id) {
        Optional<OrderItem> maybeOrderItem = orderItemRepository.findById(id);
        maybeOrderItem.ifPresent(orderItemRepository::delete);
        return maybeOrderItem.isPresent();
    }

    public List<OrderItemReadDto> getOrderItemsByOrderId(Integer orderId) {
        return orderItemRepository.findOrderItemsByOrderId(orderId)
                .stream().map(orderItemReadMapper::map)
                .collect(Collectors.toList());
    }


//    public void deleteAllByCartId(Integer id) {
//        List<OrderItem> allByCartId = orderItemRepository.findAllByCartId(id);
//        log.info("OrderItemService - deleteAllByCartId - allByCartId");
//        orderItemRepository.deleteAllByCartId(id);
//        log.info("OrderItemService - deleteAllByCartId - finish");
//
//    }

    public BigDecimal getTotalPrice(Integer orderId) {
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(orderId);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            BigDecimal price = orderItem.getProduct().getPrice();
            BigDecimal quantity = new BigDecimal(orderItem.getQuantity());
            BigDecimal itemTotalPrice = price.multiply(quantity);
            totalPrice = totalPrice.add(itemTotalPrice);
        }
        return totalPrice;
    }
}
