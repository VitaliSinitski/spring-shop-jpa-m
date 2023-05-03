package com.vitali.validation;

import com.vitali.validation.impl.PasswordNotBlankAndSizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordNotBlankAndSizeValidator.class})
public @interface PasswordNotBlankAndSize {
    String message() default "Password length must be between {min} and {max}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min() default 2;
    int max() default 20;
    boolean checkOnCreate() default true;

}

