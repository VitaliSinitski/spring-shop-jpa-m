package com.vitali.services;

import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.entities.OrderItem;
import com.vitali.mappers.orderItem.OrderItemCreateMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import com.vitali.repositories.OrderItemRepository;
import com.vitali.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemReadMapper orderItemReadMapper;
    private final OrderItemCreateMapper orderItemCreateMapper;

    public Integer create(OrderItemCreateDto orderItemCreateDto) {
        OrderItem orderItemEntity = orderItemCreateMapper.map(orderItemCreateDto);
        return orderItemRepository.save(orderItemEntity).getId();
    }

    public Optional<OrderItemReadDto> findById(Integer id) {
        return orderItemRepository.findById(id)
                .map(orderItemReadMapper::map);
    }

    public List<OrderItemReadDto> findAllByCartId(Long id) {
        return orderItemRepository.findOrderItemsByCartId(id)
                .stream()
                .map(orderItemReadMapper::map)
                .collect(Collectors.toList());
    }

    public boolean findOrderItemByProductId(Integer id) {
        return orderItemRepository.existsOrderItemByProductId(id);
    }

    public boolean delete(Integer id) {
        Optional<OrderItem> maybeOrderItem = orderItemRepository.findById(id);
        maybeOrderItem.ifPresent(orderItemRepository::delete);
        return maybeOrderItem.isPresent();
    }

}