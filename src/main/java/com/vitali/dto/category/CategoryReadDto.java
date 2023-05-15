package com.vitali.dto.category;

import com.vitali.dto.product.ProductReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryReadDto {
    private Integer id;
    private String name;
}
