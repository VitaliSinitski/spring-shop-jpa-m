package com.vitali.mappers.product;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.database.entities.Product;
import com.vitali.mappers.Mapper;
import com.vitali.mappers.category.CategoryReadMapper;
import com.vitali.mappers.producer.ProducerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    private final ProducerReadMapper producerReadMapper;
    private final CategoryReadMapper categoryReadMapper;

    @Override
    public ProductReadDto map(Product object) {
        return ProductReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .quantity(object.getQuantity())
                .image(object.getImage())
                .category(Optional.ofNullable(object.getCategory())
                        .map(categoryReadMapper::map).orElse(null))
                .producer(Optional.ofNullable(object.getProducer())
                        .map(producerReadMapper::map).orElse(null))
                .build();
    }
}
