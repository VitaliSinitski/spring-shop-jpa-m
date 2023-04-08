package com.vitali.mapper.product;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.entity.Product;
import com.vitali.mapper.Mapper;
import com.vitali.mapper.category.CategoryReadMapper;
import com.vitali.mapper.producer.ProducerReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
                .image(object.getImage()) // можно добавить уникальный префикс (!дублирование имен)
                .category(Optional.ofNullable(object.getCategory())
                        .map(categoryReadMapper::map).orElse(null))
                .producer(Optional.ofNullable(object.getProducer())
                        .map(producerReadMapper::map).orElse(null))
                .build();
    }

    public List<ProductReadDto> mapList(List<Product> objects) {
        if (objects == null || objects.isEmpty()) {
            return Collections.emptyList();
        }
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

//    public CriteriaObject convertToDtoCriteriaResult(CriteriaObject criteriaObject) {
//        return CriteriaObject.builder()
//                .categoryId(criteriaObject.getCategoryId())
//                .producerId(criteriaObject.getProducerId())
//                .currentPage(criteriaObject.getCurrentPage())
//                .recordsPerPage(criteriaObject.getRecordsPerPage())
//                .pagesNum(criteriaObject.getPagesNum())
//                .items(mapListFrom(criteriaObject.getItems()))
//                .sortDirection(criteriaObject.getSortDirection())
//                .orderField(criteriaObject.getOrderField())
//                .build();
//    }
}
