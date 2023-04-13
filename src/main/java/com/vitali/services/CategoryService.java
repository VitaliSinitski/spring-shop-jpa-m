package com.vitali.services;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.mappers.category.CategoryReadMapper;
import com.vitali.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryReadMapper categoryReadMapper;

    public List<CategoryReadDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryReadMapper::map)
                .collect(Collectors.toList());
    }

}
