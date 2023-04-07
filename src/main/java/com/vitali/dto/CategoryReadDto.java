package com.vitali.dto;

import com.vitali.entity.Producer;
import com.vitali.entity.Product;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CategoryReadDto {
    Integer id;
    String name;
    Set<ProducerReadDto> producers;
    Set<ProductReadDto> products;
}
