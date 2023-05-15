package com.vitali.services;

import com.querydsl.core.types.Predicate;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.QProduct;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.exception.NotEnoughStockException;
import com.vitali.exception.OutOfStockException;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import com.vitali.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vitali.util.MockUtils.PRODUCT_CREATE_DTO_ONE;
import static com.vitali.util.MockUtils.PRODUCT_FILTER;
import static com.vitali.util.MockUtils.PRODUCT_LIST;
import static com.vitali.util.MockUtils.PRODUCT_ONE;
import static com.vitali.util.MockUtils.PRODUCT_ONE_WITH_IMAGE_STRING;
import static com.vitali.util.MockUtils.PRODUCT_READ_DTO_ONE;
import static com.vitali.util.MockUtils.UPDATED_PRODUCT_ONE;
import static com.vitali.util.TestConstants.*;
import static com.vitali.util.TestConstants.PRODUCT_ID_ONE;
import static com.vitali.util.TestConstants.PRODUCT_IMAGE_STRING;
import static com.vitali.util.TestConstants.PRODUCT_NAME;
import static com.vitali.util.TestConstants.QUANTITY_FIVE;
import static com.vitali.util.TestConstants.QUANTITY_TEN;
import static com.vitali.util.TestConstants.QUANTITY_THREE;
import static com.vitali.util.TestConstants.QUANTITY_TWO;
import static com.vitali.util.TestConstants.QUANTITY_ZERO;
import static com.vitali.util.TestConstants.SIZE_ONE;
import static com.vitali.util.TestConstants.SIZE_TWO;
import static com.vitali.util.TestConstants.SIZE_ZERO;
import static com.vitali.util.TestConstants.TEN;
import static com.vitali.util.TestConstants.TIMES_ONE;
import static com.vitali.util.TestConstants.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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


    // findAll with ProductFilter
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

    // findAll
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

    // findById
    @Test
    public void findByIdSuccess() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.of(PRODUCT_ONE));
        when(productReadMapper.map(PRODUCT_ONE)).thenReturn(PRODUCT_READ_DTO_ONE);

        // when
        ProductReadDto result = productService.findById(PRODUCT_ID_ONE);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(PRODUCT_READ_DTO_ONE);
    }

    // create
    @Test
    public void createSuccess() {
        // given
        when(productCreateMapper.map(PRODUCT_CREATE_DTO_ONE)).thenReturn(PRODUCT_ONE);
        when(productRepository.save(PRODUCT_ONE)).thenReturn(PRODUCT_ONE);
        when(productReadMapper.map(PRODUCT_ONE)).thenReturn(PRODUCT_READ_DTO_ONE);

        // when
        ProductReadDto actualProductReadDto = productService.create(PRODUCT_CREATE_DTO_ONE);

        // then
        assertThat(actualProductReadDto).isEqualTo(PRODUCT_READ_DTO_ONE);
    }

    // update
    @Test
    public void updateSuccess() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.of(PRODUCT_ONE));
        when(productCreateMapper.map(PRODUCT_CREATE_DTO_ONE, PRODUCT_ONE)).thenReturn(UPDATED_PRODUCT_ONE);
        when(productRepository.saveAndFlush(UPDATED_PRODUCT_ONE)).thenReturn(UPDATED_PRODUCT_ONE);
        when(productReadMapper.map(UPDATED_PRODUCT_ONE)).thenReturn(PRODUCT_READ_DTO_ONE);

        // when
        ProductReadDto actualProductReadDto = productService.update(PRODUCT_ID_ONE, PRODUCT_CREATE_DTO_ONE);

        // then
        assertThat(actualProductReadDto).isEqualTo(PRODUCT_READ_DTO_ONE);
    }

    @Test
    public void updateProductNotFoundThrowEntityNotFoundException() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.empty());

        // when and then
        assertThatThrownBy(() -> productService.update(PRODUCT_ID_ONE, PRODUCT_CREATE_DTO_ONE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product with id: " + PRODUCT_ID_ONE + " not found");
    }

    // updateProductQuantityByCartItem
    @Test
    public void updateProductQuantityByCartItemSuccess() {
        // Given
        Product product = new Product();
        product.setId(PRODUCT_ID_ONE);
        product.setName(PRODUCT_NAME);
        product.setQuantity(QUANTITY_TEN);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(QUANTITY_FIVE);

        when(productRepository.save(product)).thenReturn(product);

        // When
        boolean result = productService.updateProductQuantityByCartItem(cartItem);

        // Then
        assertThat(result).isTrue();
        assertThat(product.getQuantity()).isEqualTo(QUANTITY_FIVE);
        verify(productRepository, times(TIMES_ONE)).save(product);
    }

    @Test
    public void updateProductQuantityByCartItemNotEnoughStockException() {
        // Given
        CartItem cartItem = new CartItem();
        Product product = new Product();
        product.setId(PRODUCT_ID_ONE);
        product.setName(PRODUCT_NAME);
        product.setQuantity(QUANTITY_TWO);
        cartItem.setProduct(product);
        cartItem.setQuantity(QUANTITY_THREE);

        // When
        Throwable throwable = catchThrowable(() -> productService.updateProductQuantityByCartItem(cartItem));

        // Then
        assertThat(throwable).isInstanceOf(NotEnoughStockException.class)
                .hasMessageContaining("There is not enough stock for Test Product. Current stock quantity: 2, you ordered: 3.");
    }

    @Test
    @Transactional
    public void updateProductQuantityByCartItemOutOfStockException() {
        // given
        CartItem cartItem = new CartItem();
        Product product = new Product();
        product.setId(PRODUCT_ID_ONE);
        product.setName(PRODUCT_NAME);
        product.setQuantity(QUANTITY_ZERO);
        cartItem.setProduct(product);
        cartItem.setQuantity(QUANTITY_THREE);

        // when and then
        assertThatThrownBy(() -> productService.updateProductQuantityByCartItem(cartItem))
                .isInstanceOf(OutOfStockException.class)
                .hasMessageContaining("Product: " + product.getName() + " is out of stock.");
    }


    // delete
    @Test
    public void deleteSuccess() {
        // given
        Product product = new Product();
        product.setId(PRODUCT_ID_ONE);
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.of(product));

        // when
        boolean isDeleted = productService.delete(PRODUCT_ID_ONE);

        // then
        assertThat(isDeleted).isTrue();
        verify(productRepository, times(TIMES_ONE)).delete(product);
    }

    @Test
    public void deleteProductNotFoundThrowEntityNotFoundException() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.delete(PRODUCT_ID_ONE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product with id: " + PRODUCT_ID_ONE + " not found");
    }

    // findImage
    @Test
    public void findImageProductNotFoundEmptyOptional() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.empty());

        // when
        Optional<byte[]> result = productService.findImage(PRODUCT_ID_ONE);

        // then
        assertThat(result).isEmpty();
    }


    @Test
    public void findImageProductWithoutImageEmptyOptional() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.of(PRODUCT_ONE));

        // when
        Optional<byte[]> result = productService.findImage(PRODUCT_ID_ONE);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    public void findImageSuccess() {
        // given
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.of(PRODUCT_ONE_WITH_IMAGE_STRING));
        when(imageService.get(PRODUCT_IMAGE_STRING)).thenReturn(Optional.of(PRODUCT_IMAGE_BYTES));

        // when
        Optional<byte[]> result = productService.findImage(PRODUCT_ID_ONE);

        // then
        assertThat(result).isPresent().hasValue(PRODUCT_IMAGE_BYTES);
    }
}
