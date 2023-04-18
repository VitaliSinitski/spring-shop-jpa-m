package com.vitali.validation.impl;

import com.vitali.dto.product.ProductCreateDto;
import com.vitali.validation.ProductInfo;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductInfoValidator implements ConstraintValidator<ProductInfo, ProductCreateDto> {
    @Override
    public boolean isValid(ProductCreateDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getName()) || StringUtils.hasText(value.getDescription());
    }
}
