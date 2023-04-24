package com.vitali.services;

import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.database.entities.OrderItem;
import com.vitali.mappers.orderItem.OrderItemCreateMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import com.vitali.database.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemReadMapper orderItemReadMapper;
    private final OrderItemCreateMapper orderItemCreateMapper;

    @Transactional
    public Integer create(OrderItemCreateDto orderItemCreateDto) {
        OrderItem orderItemEntity = orderItemCreateMapper.map(orderItemCreateDto);
        return orderItemRepository.save(orderItemEntity).getId();
    }

    public Optional<OrderItemReadDto> findById(Integer id) {
        return orderItemRepository.findById(id)
                .map(orderItemReadMapper::map);
    }

    public List<OrderItemReadDto> findAllByCartId(Integer id) {
        return orderItemRepository.findOrderItemsByCartId(id)
                .stream()
                .map(orderItemReadMapper::map)
                .collect(Collectors.toList());
    }

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

}
