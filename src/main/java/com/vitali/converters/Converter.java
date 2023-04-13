package com.vitali.converters;

public interface Converter<F, T> {
    T convert(F object);
}
