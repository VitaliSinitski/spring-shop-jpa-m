package com.vitali.dto.product;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.dto.producer.ProducerReadDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductReadDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String image;
    private CategoryReadDto category;
    private ProducerReadDto producer;
}
