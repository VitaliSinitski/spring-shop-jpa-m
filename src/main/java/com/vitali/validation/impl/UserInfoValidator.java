package com.vitali.validation.impl;

import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.validation.ProductInfo;
import com.vitali.validation.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateDto> {
    @Override
    public boolean isValid(UserCreateDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getUsername()) || StringUtils.hasText(value.getEmail());
    }
}
