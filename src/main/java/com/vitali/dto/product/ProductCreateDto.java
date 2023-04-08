package com.vitali.dto.product;

import lombok.Builder;
import lombok.Value;

import javax.servlet.http.Part;
import java.math.BigDecimal;

@Builder
@Value
public class ProductCreateDto {
    String name;
    String description;
    BigDecimal price;
    Integer quantity;
    Part image;
    Integer categoryId;
    Integer producerId;
}
