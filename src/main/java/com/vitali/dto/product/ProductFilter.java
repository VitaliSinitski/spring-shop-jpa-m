package com.vitali.dto.product;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.dto.producer.ProducerReadDto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFilter {
    private String name;
    private BigDecimal price;
    private CategoryReadDto category;
    private ProducerReadDto producer;
    private Sort sort;
}
