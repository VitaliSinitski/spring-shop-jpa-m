package com.vitali.dto.category;

import com.vitali.dto.product.ProductReadDto;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CategoryReadDto {
    Integer id;
    String name;
//    Set<ProducerReadDto> producers;
//    Set<ProductReadDto> products;
}
