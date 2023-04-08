package com.vitali.dto.producer;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.dto.category.CategoryReadDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Builder
@Value
public class ProducerReadDto {
    Integer id;
    String name;
//    Set<CategoryReadDto> categories;
//    List<ProductReadDto> products;
}
