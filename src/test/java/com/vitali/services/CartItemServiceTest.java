package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.database.repositories.CartItemRepository;
import com.vitali.database.repositories.CartRepository;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.mappers.cartItem.CartItemCreateMapper;
import com.vitali.mappers.cartItem.CartItemReadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemReadMapper cartItemReadMapper;
    @Mock
    private CartItemCreateMapper cartItemCreateMapper;

    @InjectMocks
    private CartItemService cartItemService;

    private Cart cart;
    private CartItem cartItem;
    private CartItemReadDto cartItemReadDto;
    private Integer quantity;
    private Integer cartId;
    private Integer productId;
    private Integer cartItemId;

    @BeforeEach
    void setUp() {
        cartId = 1;
        cartItemId = 2;
        productId = 1;
        quantity = 2;
        cart = new Cart();
        cart.setId(cartId);
        cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setQuantity(quantity);
        cartItemReadDto = new CartItemReadDto();
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(10));
        cartItem.setProduct(product);
        cart.addCartItem(cartItem);
    }

    @Test
    public void createCartItem() {
        // given
        CartItemCreateDto cartItemCreateDto = CartItemCreateDto.builder()
                .quantity(quantity)
                .productId(productId)
                .cartId(cartId)
                .build();
        when(cartItemCreateMapper.map(cartItemCreateDto)).thenReturn(cartItem);
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        // when
        Integer createdCartItemID = cartItemService.create(quantity, productId, cartId);

        // then
        assertThat(createdCartItemID).isEqualTo(cartItem.getId());
        verify(cartItemCreateMapper).map(cartItemCreateDto);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    public void findAllByCartId() {
        // given
        List<CartItem> cartItemList = Arrays.asList(cartItem);
        List<CartItemReadDto> cartItemReadDtoList = Arrays.asList(cartItemReadDto);
        when(cartItemRepository.findCartItemsByCartId(cartId)).thenReturn(cartItemList);
        when(cartItemReadMapper.map(cartItem)).thenReturn(cartItemReadDto);

        // when
        List<CartItemReadDto> result = cartItemService.findAllByCartId(cartId);

        // then
        assertThat(result).isEqualTo(cartItemReadDtoList);
        verify(cartItemRepository).findCartItemsByCartId(cartId);
        verify(cartItemReadMapper).map(cartItem);
    }


    @Test
    public void delete_cartItemExists_cartItemDeleted() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));

        // when
        boolean isDeleted = cartItemService.delete(cartId, cartItemId);

        // then
        assertTrue(isDeleted);
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository).findById(cartItemId);
        verify(cartRepository).save(cart);

    }

    @Test
    public void delete_cartItemDoesNotExist_EntityNotFoundExceptionThrown() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> cartItemService.delete(cartId, cartItemId));

        // then
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository).findById(cartItemId);
        verifyNoMoreInteractions(cartRepository, cartItemRepository);
    }

    @Test
    public void delete_cartDoesNotExist_EntityNotFoundExceptionThrown() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> cartItemService.delete(cartId, cartItemId));

        // then
        verify(cartRepository).findById(cartId);
        verifyNoMoreInteractions(cartRepository, cartItemRepository);
    }



    @Test
    public void deleteAllByCartId_shouldDeleteAllCartItemsAndReturnTrue() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(cartId)).thenReturn(Arrays.asList(cartItem));

        // when
        boolean result = cartItemService.deleteAllByCartId(cartId);

        // then
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository).findAllByCartId(cartId);
        verify(cartRepository).save(cart);
        assertThat(result).isTrue();
        assertThat(cart.getCartItems()).isEmpty();
    }

    @Test
    public void givenCartId_whenDeleteAllByCartId_thenDeleteAllCartItems() {
        // given
        List<CartItem> cartItemsToDelete = Arrays.asList(cartItem);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(cartId)).thenReturn(cartItemsToDelete);

        // when
        boolean result = cartItemService.deleteAllByCartId(cartId);

        // then
        assertTrue(result);
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository).findAllByCartId(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    public void givenEmptyCartId_whenDeleteAllByCartId_thenReturnFalse() {
        // given
        Integer cartId = 1;
        Cart cart = new Cart();
        cart.setId(cartId);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(cartId)).thenReturn(Collections.emptyList());

        // when
        boolean result = cartItemService.deleteAllByCartId(cartId);

        // then
        assertFalse(result);
        assertTrue(cart.getCartItems().isEmpty());
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository).findAllByCartId(cartId);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void givenInvalidCartId_whenDeleteAllByCartId_thenThrowEntityNotFoundException() {
        // given
        Integer cartId = 1;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> cartItemService.deleteAllByCartId(cartId));
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository, never()).findAllByCartId(cartId);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    // тесты для метода updateCartItemQuantity()

    @Test
    public void givenCartIdAndCartItemId_whenUpdateCartItemQuantity_thenUpdateCartItemQuantity() {
        // given
        Integer cartId = 1;
        Integer cartItemId = 2;
        Integer quantity = 3;
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        when(cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)).thenReturn(Optional.of(cartItem));

        // when
        cartItemService.updateCartItemQuantity(cartId, cartItemId, quantity);

        // then
        assertEquals(quantity, cartItem.getQuantity());
        verify(cartItemRepository).findCartItemByIdAndCartId(cartItemId, cartId);
        verify(cartItemRepository).save(cartItem);
    }


    @Test
    public void deleteAllByCartId_whenCartIsEmpty_thenReturnFalse() {
        // given
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(cartId)).thenReturn(Collections.emptyList());

        // when
        boolean result = cartItemService.deleteAllByCartId(cartId);

        // then
        assertFalse(result);
        verify(cartRepository).findById(cartId);
        verify(cartItemRepository).findAllByCartId(cartId);
        verifyNoMoreInteractions(cartRepository, cartItemRepository);
    }

    @Test
    public void updateCartItemQuantity_whenCartItemExists_thenUpdateQuantity() {
        // given
        when(cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)).thenReturn(Optional.of(cartItem));
        ArgumentCaptor<CartItem> cartItemArgumentCaptor = ArgumentCaptor.forClass(CartItem.class);

        // when
        cartItemService.updateCartItemQuantity(cartId, cartItemId, quantity);

        // then
        verify(cartItemRepository).findCartItemByIdAndCartId(cartItemId, cartId);
        verify(cartItemRepository).save(cartItemArgumentCaptor.capture());
        CartItem updatedCartItem = cartItemArgumentCaptor.getValue();
        assertThat(updatedCartItem.getQuantity()).isEqualTo(quantity);
        assertThat(updatedCartItem.getId()).isEqualTo(cartItemId);
        verifyNoMoreInteractions(cartItemRepository);
    }



    @Test
    void updateCartItemQuantity() {
    }

    @Test
    void getTotalPrice() {
    }
}