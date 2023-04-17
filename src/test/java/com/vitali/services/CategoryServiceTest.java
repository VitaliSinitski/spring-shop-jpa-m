package com.vitali.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.vitali.dto.category.CategoryReadDto;
import com.vitali.mappers.category.CategoryReadMapper;
import com.vitali.database.entities.Category;
import com.vitali.database.repositories.CategoryRepository;
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
        // Mocking the repositories to return some sample categories
        Category category1 = new Category(1, "Category 1", null, null);
        Category category2 = new Category(2, "Category 2", null, null);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Mocking the mappers to return CategoryReadDto objects for the categories
        CategoryReadDto dto1 = new CategoryReadDto(1, "Category 1");
        CategoryReadDto dto2 = new CategoryReadDto(2, "Category 2");
        when(categoryReadMapper.map(category1)).thenReturn(dto1);
        when(categoryReadMapper.map(category2)).thenReturn(dto2);

        // Calling the services method
        List<CategoryReadDto> categories = categoryService.findAll();

        // Asserting that the returned list contains the expected CategoryReadDto objects
        assertEquals(Arrays.asList(dto1, dto2), categories);
    }
}
