package com.vitali.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.mapper.category.CategoryReadMapper;
import com.vitali.entity.Category;
import com.vitali.repository.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryReadMapper categoryReadMapper;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryReadMapper = mock(CategoryReadMapper.class);
        categoryService = new CategoryService(categoryRepository, categoryReadMapper);
    }

    @Test
    void testFindAll() {
        // Mocking the repository to return some sample categories
        Category category1 = new Category(1, "Category 1", null, null);
        Category category2 = new Category(2, "Category 2", null, null);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Mocking the mapper to return CategoryReadDto objects for the categories
        CategoryReadDto dto1 = new CategoryReadDto(1, "Category 1");
        CategoryReadDto dto2 = new CategoryReadDto(2, "Category 2");
        when(categoryReadMapper.map(category1)).thenReturn(dto1);
        when(categoryReadMapper.map(category2)).thenReturn(dto2);

        // Calling the service method
        List<CategoryReadDto> categories = categoryService.findAll();

        // Asserting that the returned list contains the expected CategoryReadDto objects
        assertEquals(Arrays.asList(dto1, dto2), categories);
    }
}
