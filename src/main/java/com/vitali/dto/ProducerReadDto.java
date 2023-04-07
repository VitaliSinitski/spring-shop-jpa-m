package com.vitali.dto;

import com.vitali.entity.Category;
import com.vitali.entity.Product;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Builder
@Value
public class ProducerReadDto {
    Integer id;
    String name;
    Set<CategoryReadDto> categories;
    List<ProductReadDto> products;

}
