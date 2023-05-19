package com.vitali.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFilter {
    private String name;
    private BigDecimal price;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer categoryId;
    private Integer producerId;
    private String sortField;
    private String sortDirection;
}
