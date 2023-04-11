package com.vitali.converter;

public interface Converter<F, T> {
    T convert(F object);
}
