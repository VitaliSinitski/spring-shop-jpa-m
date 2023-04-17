package com.vitali.dto.product;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.math.BigDecimal;

@Builder
@Value
public class ProductCreateDto {
    String name;
    String description;
    BigDecimal price;
    Integer quantity;
    MultipartFile image;
    Integer categoryId;
    Integer producerId;
}
