package com.vitali.mapper.category;

import com.vitali.dto.CategoryCreateDto;
import com.vitali.entity.Category;
import com.vitali.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryCreateMapper implements Mapper<CategoryCreateDto, Category> {
    @Override
    public Category map(CategoryCreateDto object) {
        return Category.builder()
                .name(object.getName())
                .build();
    }

    @Override
    public List<Category> mapList(List<CategoryCreateDto> objects) {
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
