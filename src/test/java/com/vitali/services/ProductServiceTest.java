package com.vitali.services;

import com.querydsl.core.types.Predicate;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.QProduct;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCreateMapper productCreateMapper;
    @Mock
    private ProductReadMapper productReadMapper;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private ProductService productService;

    @Test
    public void findAllWithProductFilterSuccess() {
        //given
        Pageable pageable = PageRequest.of(ZERO, TEN);
        QProduct product = QProduct.product;
        Predicate predicate = product.name.containsIgnoreCase(PRODUCT_FILTER.getName())
                .and(product.category.id.eq(PRODUCT_FILTER.getCategoryId()))
                .and(product.producer.id.eq(PRODUCT_FILTER.getProducerId()))
                .and(product.price.eq(PRODUCT_FILTER.getPrice()))
                .and(product.price.goe(PRODUCT_FILTER.getMinPrice()))
                .and(product.price.loe(PRODUCT_FILTER.getMaxPrice()));
        Page<Product> products = new PageImpl<>(Collections.singletonList(new Product()), pageable, 1L);

        when(productRepository.findAll(predicate, pageable)).thenReturn(products);
        when(productReadMapper.map(any(Product.class))).thenReturn(PRODUCT_READ_DTO_ONE);

        //when
        Page<ProductReadDto> result = productService.findAll(PRODUCT_FILTER, pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(SIZE_ONE);
        verify(productRepository).findAll(predicate, pageable);
        verify(productReadMapper).map(any(Product.class));
    }

    @Test
    public void findAllSuccess() {
        // given
        when(productRepository.findAll()).thenReturn(PRODUCT_LIST);
        when(productReadMapper.map(PRODUCT_ONE)).thenReturn(PRODUCT_READ_DTO_ONE);

        // when
        List<ProductReadDto> result = productService.findAll();

        // then
        assertThat(result).hasSize(SIZE_TWO);
        assertThat(result.get(SIZE_ZERO).getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void updateProductQuantityByCartItem() {
    }

    @Test
    void delete() {
    }

    @Test
    void findImage() {
    }
}