package com.vitali.mappers.category;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.database.entities.Category;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {

    @Override
    public CategoryReadDto map(Category object) {
        return CategoryReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }
}
