package com.vitali.services;

import com.vitali.database.repositories.CategoryRepository;
import com.vitali.dto.category.CategoryReadDto;
import com.vitali.mappers.category.CategoryReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.vitali.util.MockUtils.CATEGORY;
import static com.vitali.util.MockUtils.CATEGORY_LIST;
import static com.vitali.util.MockUtils.CATEGORY_READ_DTO;
import static com.vitali.util.TestConstants.CATEGORY_NAME;
import static com.vitali.util.TestConstants.SIZE_ONE;
import static com.vitali.util.TestConstants.SIZE_ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryReadMapper categoryReadMapper;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testFindAll() {
        // given
        when(categoryRepository.findAll()).thenReturn(CATEGORY_LIST);
        when(categoryReadMapper.map(CATEGORY)).thenReturn(CATEGORY_READ_DTO);

        // when
        List<CategoryReadDto> result = categoryService.findAll();

        // then
        assertThat(result).hasSize(SIZE_ONE);
        assertThat(result.get(SIZE_ZERO).getName()).isEqualTo(CATEGORY_NAME);
    }
}