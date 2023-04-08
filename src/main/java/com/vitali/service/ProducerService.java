package com.vitali.service;

import com.vitali.mapper.order.OrderCreateMapper;
import com.vitali.mapper.order.OrderReadMapper;
import com.vitali.mapper.producer.ProducerCreateMapper;
import com.vitali.mapper.producer.ProducerReadMapper;
import com.vitali.repository.OrderRepository;
import com.vitali.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository producerRepository;
    private final ProducerReadMapper producerReadMapper;
    private final ProducerCreateMapper producerCreateMapper;
}
