package com.vitali.validation;

import com.vitali.validation.impl.ProductInfoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductInfo {
    String message() default "The name and description of the product should be filled in.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
