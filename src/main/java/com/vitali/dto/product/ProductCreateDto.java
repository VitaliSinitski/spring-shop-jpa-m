package com.vitali.dto.product;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@Value
public class ProductCreateDto {
    @NotNull
    @Size(min = 2, max = 64)
    String name;
    @NotNull
    @Size(min = 5, max = 64)
    String description;
    @NotNull
    BigDecimal price;
    @NotNull
    Integer quantity;
    MultipartFile image;
    Integer categoryId;
    Integer producerId;
}
