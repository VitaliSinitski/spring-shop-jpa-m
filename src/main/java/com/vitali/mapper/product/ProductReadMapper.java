package com.vitali.mapper.product;

import com.vitali.dto.ProductReadDto;
import com.vitali.entity.Product;
import com.vitali.mapper.Mapper;
import com.vitali.mapper.producer.ProducerReadMapper;
import com.vitali.shop.util.CriteriaObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    private final ProducerReadMapper producerReadMapper;
    private final CategoryReadMapper categoryReadMapper;

    @Override
    public ProductReadDto mapFrom(Product object) {
        return ProductReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .quantity(object.getQuantity())
                .image(object.getImage()) // можно добавить уникальный префикс (!дублирование имен)
                .category(Optional.ofNullable(object.getCategory())
                        .map(categoryReadMapper::mapFrom).orElse(null))
                .producer(Optional.ofNullable(object.getProducer())
                        .map(producerReadMapper::mapFrom).orElse(null))
                .build();
    }

    @Override
    public List<ProductReadDto> mapListFrom(List<Product> objects) {
        return objects.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }

    public CriteriaObject convertToDtoCriteriaResult(CriteriaObject criteriaObject) {
        return CriteriaObject.builder()
                .categoryId(criteriaObject.getCategoryId())
                .producerId(criteriaObject.getProducerId())
                .currentPage(criteriaObject.getCurrentPage())
                .recordsPerPage(criteriaObject.getRecordsPerPage())
                .pagesNum(criteriaObject.getPagesNum())
                .items(mapListFrom(criteriaObject.getItems()))
                .sortDirection(criteriaObject.getSortDirection())
                .orderField(criteriaObject.getOrderField())
                .build();
    }
}
