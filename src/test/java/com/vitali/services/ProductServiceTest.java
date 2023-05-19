package com.vitali.services;

import com.querydsl.core.types.Predicate;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.QProduct;
import com.vitali.database.repositories.CategoryRepository;
import com.vitali.database.repositories.ProducerRepository;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.exception.NotEnoughStockException;
import com.vitali.exception.OutOfStockException;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import static com.vitali.util.MockUtils.*;
import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProducerRepository producerRepository;
    @Mock
    private ProductReadMapper productReadMapper;
    @Mock
    private ProductCreateMapper productCreateMapper;
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
        when(categoryRepository.findById(PRODUCT_CREATE_DTO_ONE.getCategoryId())).thenReturn(Optional.of(CATEGORY));
        when(producerRepository.findById(PRODUCT_CREATE_DTO_ONE.getProducerId())).thenReturn(Optional.of(PRODUCER));
        when(productCreateMapper.map(PRODUCT_CREATE_DTO_ONE)).thenReturn(PRODUCT);
        when(productRepository.save(any(Product.class))).thenReturn(PRODUCT);
        when(productReadMapper.map(PRODUCT)).thenReturn(PRODUCT_READ_DTO_ONE);

        // when
        ProductReadDto createdProduct = productService.create(PRODUCT_CREATE_DTO_ONE);

        // then
        assertThat(createdProduct).isEqualTo(PRODUCT_READ_DTO_ONE);
        verify(categoryRepository).findById(PRODUCT_CREATE_DTO_ONE.getCategoryId());
        verify(producerRepository).findById(PRODUCT_CREATE_DTO_ONE.getProducerId());
        verify(productCreateMapper).map(PRODUCT_CREATE_DTO_ONE);
        verify(productRepository).save(PRODUCT);
        verify(productReadMapper).map(PRODUCT);
    }

    @Test
    public void createCategoryNotFoundThrowEntityNotFoundException() {
        // given
        when(categoryRepository.findById(PRODUCT_CREATE_DTO.getCategoryId())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.create(PRODUCT_CREATE_DTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Category not found");

        verify(categoryRepository).findById(PRODUCT_CREATE_DTO.getCategoryId());
        verifyNoInteractions(producerRepository);
        verifyNoInteractions(productCreateMapper);
        verifyNoInteractions(productRepository);
        verifyNoInteractions(productReadMapper);
    }

    @Test
    public void createProducerNotFoundThrowEntityNotFoundException() {
        // given
        when(categoryRepository.findById(PRODUCT_CREATE_DTO.getCategoryId())).thenReturn(Optional.of(CATEGORY));
        when(producerRepository.findById(PRODUCT_CREATE_DTO.getProducerId())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.create(PRODUCT_CREATE_DTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Producer not found");

        verify(categoryRepository).findById(PRODUCT_CREATE_DTO.getCategoryId());
        verify(producerRepository).findById(PRODUCT_CREATE_DTO.getProducerId());
        verifyNoInteractions(productCreateMapper);
        verifyNoInteractions(productRepository);
        verifyNoInteractions(productReadMapper);
    }

    // update


    @Test
    public void updateSuccess() {
        // given
        when(categoryRepository.findById(PRODUCT_CREATE_DTO.getCategoryId())).thenReturn(Optional.of(CATEGORY));
        when(producerRepository.findById(PRODUCT_CREATE_DTO.getProducerId())).thenReturn(Optional.of(PRODUCER));
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.of(PRODUCT));
        when(productCreateMapper.map(PRODUCT_CREATE_DTO, PRODUCT)).thenReturn(MAPPED_PRODUCT);
        when(productRepository.saveAndFlush(MAPPED_PRODUCT)).thenReturn(PRODUCT);
        when(productReadMapper.map(PRODUCT)).thenReturn(PRODUCT_READ_DTO);

        // when
        ProductReadDto updatedProduct = productService.update(PRODUCT_ID_ONE, PRODUCT_CREATE_DTO);

        // then
        assertThat(updatedProduct).isEqualTo(PRODUCT_READ_DTO);
        verify(categoryRepository).findById(PRODUCT_CREATE_DTO.getCategoryId());
        verify(producerRepository).findById(PRODUCT_CREATE_DTO.getProducerId());
        verify(productRepository).findById(PRODUCT_ID_ONE);
        verify(productCreateMapper).map(PRODUCT_CREATE_DTO, PRODUCT);
        verify(productRepository).saveAndFlush(MAPPED_PRODUCT);
        verify(productReadMapper).map(PRODUCT);
    }

    @Test
    public void updateCategoryNotFoundThrowEntityNotFoundException() {
        // given
        when(categoryRepository.findById(PRODUCT_CREATE_DTO.getCategoryId())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.update(PRODUCT_ID_ONE, PRODUCT_CREATE_DTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Category not found");

        verify(categoryRepository).findById(PRODUCT_CREATE_DTO.getCategoryId());
        verifyNoInteractions(producerRepository);
        verifyNoInteractions(productRepository);
        verifyNoInteractions(productCreateMapper);
        verifyNoInteractions(productReadMapper);
    }

    @Test
    public void updateProducerNotFoundThrowEntityNotFoundException() {
        // given
        when(categoryRepository.findById(PRODUCT_CREATE_DTO.getCategoryId())).thenReturn(Optional.of(CATEGORY));
        when(producerRepository.findById(PRODUCT_CREATE_DTO.getProducerId())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.update(PRODUCT_ID_ONE, PRODUCT_CREATE_DTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Producer not found");

        verify(categoryRepository).findById(PRODUCT_CREATE_DTO.getCategoryId());
        verify(producerRepository).findById(PRODUCT_CREATE_DTO.getProducerId());
        verifyNoInteractions(productRepository);
        verifyNoInteractions(productCreateMapper);
        verifyNoInteractions(productReadMapper);
    }

    @Test
    public void updateProductNotFoundThrowEntityNotFoundException() {
        // given
        when(categoryRepository.findById(PRODUCT_CREATE_DTO.getCategoryId())).thenReturn(Optional.of(CATEGORY));
        when(producerRepository.findById(PRODUCT_CREATE_DTO.getProducerId())).thenReturn(Optional.of(PRODUCER));
        when(productRepository.findById(PRODUCT_ID_ONE)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.update(PRODUCT_ID_ONE, PRODUCT_CREATE_DTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product with id: " + PRODUCT_ID_ONE + " not found");

        verify(categoryRepository).findById(PRODUCT_CREATE_DTO.getCategoryId());
        verify(producerRepository).findById(PRODUCT_CREATE_DTO.getProducerId());
        verify(productRepository).findById(PRODUCT_ID_ONE);
        verifyNoInteractions(productCreateMapper);
        verifyNoInteractions(productReadMapper);
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
