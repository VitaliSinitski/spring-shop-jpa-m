package com.vitali.mapper;

import java.util.List;

public interface Mapper<F, T> {

    T map(F object);
    List<T> mapList(List<F> objects);

    default T map(F fromObject, T toObject) {
        return toObject;
    }

}
