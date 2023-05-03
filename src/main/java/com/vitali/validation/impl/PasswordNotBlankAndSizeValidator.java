package com.vitali.validation.impl;

import com.vitali.validation.PasswordNotBlankAndSize;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordNotBlankAndSizeValidator implements ConstraintValidator<PasswordNotBlankAndSize, String> {
    private int min;
    private int max;
    private boolean checkOnCreate;

    @Override
    public void initialize(PasswordNotBlankAndSize constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        checkOnCreate = constraintAnnotation.checkOnCreate();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (checkOnCreate) {
            return StringUtils.isNotBlank(value) && value.length() >= min && value.length() <= max;
        } else {
            return true;
        }
    }
}