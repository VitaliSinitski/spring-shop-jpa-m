package com.vitali.services;

import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.database.entities.OrderItem;
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

    public List<OrderItemReadDto> findAllByOrderId(Integer orderId) {
        return orderItemRepository.findOrderItemsByOrderId(orderId).stream()
                .map(orderItemReadMapper::map)
                .collect(Collectors.toList());
    }

//    public List<OrderItemReadDto> getOrderItemsByOrderId(Integer orderId) {
//        return orderItemRepository.findOrderItemsByOrderId(orderId).stream()
//                .map(orderItemReadMapper::map)
//                .collect(Collectors.toList());
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
