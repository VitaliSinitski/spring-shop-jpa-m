package com.vitali.services;

import com.vitali.dto.producer.ProducerReadDto;
import com.vitali.mappers.producer.ProducerCreateMapper;
import com.vitali.mappers.producer.ProducerReadMapper;
import com.vitali.database.repositories.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProducerService {
    private final ProducerRepository producerRepository;
    private final ProducerReadMapper producerReadMapper;

    public List<ProducerReadDto> findAll() {
        return producerRepository.findAll().stream()
                .map(producerReadMapper::map)
                .collect(Collectors.toList());
    }

}
