package com.vitali.mappers.category;

import com.vitali.dto.category.CategoryCreateDto;
import com.vitali.database.entities.Category;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryCreateMapper implements Mapper<CategoryCreateDto, Category> {
    @Override
    public Category map(CategoryCreateDto object) {
        return Category.builder()
                .name(object.getName())
                .build();
    }

    public List<Category> mapList(List<CategoryCreateDto> objects) {
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
