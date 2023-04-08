package com.vitali.service;

import com.vitali.mapper.order.OrderCreateMapper;
import com.vitali.mapper.order.OrderReadMapper;
import com.vitali.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateMapper orderCreateMapper;
}
