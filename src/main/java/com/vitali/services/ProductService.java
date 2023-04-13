package com.vitali.services;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import com.vitali.repositories.ProductRepository;
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
