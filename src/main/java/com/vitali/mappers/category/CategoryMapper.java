package com.vitali.mappers.category;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.entities.Category;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper implements Mapper<CategoryReadDto, Category> {
    @Override
    public Category map(CategoryReadDto object) {
        return Category.builder()
                .name(object.getName())
                .build();
    }

    public List<Category> mapList(List<CategoryReadDto> objects) {
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
