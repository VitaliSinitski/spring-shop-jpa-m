package com.vitali.mapper.category;

import com.vitali.dto.CategoryReadDto;
import com.vitali.entity.Category;
import com.vitali.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper implements Mapper<CategoryReadDto, Category> {
    @Override
    public Category map(CategoryReadDto object) {
        return Category.builder()
                .name(object.getName())
                .build();
    }

    @Override
    public List<Category> mapList(List<CategoryReadDto> objects) {
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
