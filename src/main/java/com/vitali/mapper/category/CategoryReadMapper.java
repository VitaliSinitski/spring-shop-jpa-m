package com.vitali.mapper.category;

import com.vitali.dto.CategoryReadDto;
import com.vitali.entity.Category;
import com.vitali.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {
    @Override
    public CategoryReadDto map(Category object) {
        return CategoryReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public List<CategoryReadDto> mapList(List<Category> objects) {
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
