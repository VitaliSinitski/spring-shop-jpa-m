package com.vitali.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryCreateDto {
    String name;
}
