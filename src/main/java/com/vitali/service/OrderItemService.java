package com.vitali.service;

import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.entity.OrderItem;
import com.vitali.entity.Product;
import com.vitali.mapper.order.OrderCreateMapper;
import com.vitali.mapper.order.OrderReadMapper;
import com.vitali.mapper.orderItem.OrderItemCreateMapper;
import com.vitali.mapper.orderItem.OrderItemReadMapper;
import com.vitali.repository.OrderItemRepository;
import com.vitali.repository.OrderRepository;
import com.vitali.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemReadMapper orderItemReadMapper;
    private final OrderItemCreateMapper orderItemCreateMapper;
    private final ProductRepository productRepository;

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
