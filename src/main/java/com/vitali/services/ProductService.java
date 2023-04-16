package com.vitali.services;

import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import com.vitali.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
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

    public Optional<ProductReadDto> findById(Integer id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateDto productCreateDto) {
        return Optional.of(productCreateDto)
                .map(productCreateMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Integer id, ProductCreateDto productCreateDto) {
        return productRepository.findById(id)
                .map(product -> productCreateMapper.map(productCreateDto, product))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    public boolean delete(Integer id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
}
