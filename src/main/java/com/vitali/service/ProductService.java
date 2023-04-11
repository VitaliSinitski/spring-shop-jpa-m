package com.vitali.service;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.entity.Product;
import com.vitali.mapper.producer.ProducerCreateMapper;
import com.vitali.mapper.producer.ProducerReadMapper;
import com.vitali.mapper.product.ProductCreateMapper;
import com.vitali.mapper.product.ProductReadMapper;
import com.vitali.repository.ProducerRepository;
import com.vitali.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCreateMapper productCreateMapper;
    private final ProductReadMapper productReadMapper;

    public List<ProductReadDto> findAll() {
        return productRepository.findAll()
                .stream().map(productReadMapper::map)
                .collect(Collectors.toList());
    }

    public ProductReadDto findById(Integer id) {
        return productReadMapper.map(productRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
}
