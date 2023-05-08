package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.database.repositories.CartItemRepository;
import com.vitali.database.repositories.CartRepository;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.mappers.cartItem.CartItemCreateMapper;
import com.vitali.mappers.cartItem.CartItemReadMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext
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

    // create
    @Test
    public void createCartItem() {
        // given
        when(cartItemCreateMapper.map(CART_ITEM_CREATE_DTO)).thenReturn(CART_ITEM);
        when(cartItemRepository.save(CART_ITEM)).thenReturn(CART_ITEM);

        // when
        Integer createdCartItemID = cartItemService.create(QUANTITY_TWO, PRODUCT_ID_ONE, CART_ID);

        // then
        assertThat(createdCartItemID).isEqualTo(CART_ITEM.getId());
        verify(cartItemCreateMapper).map(CART_ITEM_CREATE_DTO);
        verify(cartItemRepository).save(CART_ITEM);
    }

    // findAllByCartId
    @Test
    public void findAllByCartIdSuccess() {
        // given
        when(cartItemRepository.findCartItemsByCartId(CART_ID)).thenReturn(CART_ITEM_LIST);
        when(cartItemReadMapper.map(CART_ITEM)).thenReturn(CART_ITEM_READ_DTO);

        // when
        List<CartItemReadDto> result = cartItemService.findAllByCartId(CART_ID);

        // then
        assertThat(result).isEqualTo(CART_ITEM_READ_DTO_LIST);
        verify(cartItemRepository).findCartItemsByCartId(CART_ID);
        verify(cartItemReadMapper).map(CART_ITEM);
    }

    // delete
    @Test
    public void deleteSuccess() {
        // given
        Cart cart = new Cart();
        cart.setId(CART_ID);
        cart.addCartItem(CART_ITEM);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(CART_ITEM_ID)).thenReturn(Optional.of(CART_ITEM));

        // when
        boolean isDeleted = cartItemService.delete(CART_ID, CART_ITEM_ID);

        // then
        assertTrue(isDeleted);
        verify(cartRepository).findById(CART_ID);
        verify(cartItemRepository).findById(CART_ITEM_ID);
        verify(cartRepository).save(cart);

    }

    @Test
    public void deleteCartItemNotFoundThrowEntityNotFoundException() {
        // given
        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(CART));
        when(cartItemRepository.findById(CART_ITEM_ID)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> cartItemService.delete(CART_ID, CART_ITEM_ID));

        // then
        verify(cartRepository).findById(CART_ID);
        verify(cartItemRepository).findById(CART_ITEM_ID);
        verifyNoMoreInteractions(cartRepository, cartItemRepository);
    }

    @Test
    public void deleteCartNotFoundThrowEntityNotFoundException() {
        // given
        when(cartRepository.findById(CART_ID)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> cartItemService.delete(CART_ID, CART_ITEM_ID));

        // then
        verify(cartRepository).findById(CART_ID);
        verifyNoMoreInteractions(cartRepository, cartItemRepository);
    }

    // deleteAllByCartId
    @Test
    public void deleteAllByCartIdSuccess() {
        // given
        Cart cart = new Cart();
        cart.setId(CART_ID);
        CartItem cartItem1 = CartItem.builder().quantity(QUANTITY_TWO).cart(cart).build();
        CartItem cartItem2 = CartItem.builder().quantity(QUANTITY_ONE).cart(cart).build();
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(cartItems);

        // when
        boolean result = cartItemService.deleteAllByCartId(CART_ID);

        // then
        verify(cartRepository, times(TIMES_ONE)).findById(CART_ID);
        verify(cartItemRepository, times(TIMES_ONE)).findAllByCartId(CART_ID);
        verify(cartRepository, times(TIMES_ONE)).save(cart);
        assertThat(result).isTrue();
        assertThat(cart.getCartItems()).isEmpty();
    }


    @Test
    public void deleteAllByCartIdCartItemsNotFound() {
        // given
        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(new Cart()));
        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(new ArrayList<>());

        // when
        boolean result = cartItemService.deleteAllByCartId(CART_ID);

        // then
        verify(cartRepository, times(TIMES_ONE)).findById(CART_ID);
        verify(cartItemRepository, times(TIMES_ONE)).findAllByCartId(CART_ID);
        verify(cartRepository, never()).save(any(Cart.class));
        assertThat(result).isFalse();
    }

    @Test
    public void deleteAllByCartIdCartNotFoundThrowEntityNotFoundException() {
        // given
        when(cartRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // when and then
        assertThatThrownBy(() -> cartItemService.deleteAllByCartId(CART_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cart with id: " + CART_ID + " not found");
    }


    @Test
    public void deleteAllByCartIdThrowExceptionSaveCartFails() {
        // given
        Cart cart = new Cart();
        CartItem cartItem1 = new CartItem();
        CartItem cartItem2 = new CartItem();
        cart.addCartItem(cartItem1);
        cart.addCartItem(cartItem2);

        when(cartRepository.findById(CART_ID)).thenReturn(java.util.Optional.of(cart));
        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(List.of(cartItem1, cartItem2));
        RuntimeException exception = new RuntimeException();
        doThrow(exception).when(cartRepository).save(cart);

        // when and then
        assertThatThrownBy(() -> cartItemService.deleteAllByCartId(CART_ID))
                .isSameAs(exception);
    }

    // updateCartItemQuantity

    @Test
    public void updateCartItemQuantitySuccess() {
        // given
        CartItem cartItem = new CartItem();
        cartItem.setId(CART_ITEM_ID);
        cartItem.setQuantity(QUANTITY_TWO);

        when(cartItemRepository.findCartItemByIdAndCartId(CART_ITEM_ID, CART_ID)).thenReturn(Optional.of(cartItem));

        // when
        cartItemService.updateCartItemQuantity(CART_ID, CART_ITEM_ID, QUANTITY_THREE);

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(QUANTITY_THREE);
        verify(cartItemRepository, times(TIMES_ONE)).findCartItemByIdAndCartId(CART_ITEM_ID, CART_ID);
        verify(cartItemRepository, times(TIMES_ONE)).save(cartItem);
        verifyNoMoreInteractions(cartItemRepository);
        verifyNoInteractions(cartRepository, cartItemReadMapper, cartItemCreateMapper);
    }

    @Test
    public void updateCartItemQuantityCartItemNotFoundThrowEntityNotFoundException() {
        // given
        when(cartItemRepository.findCartItemByIdAndCartId(CART_ITEM_ID, CART_ID)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> cartItemService.updateCartItemQuantity(CART_ID, CART_ITEM_ID, QUANTITY_THREE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("CartItem with id: " + CART_ITEM_ID + " not found");

        verify(cartItemRepository, times(TIMES_ONE)).findCartItemByIdAndCartId(CART_ITEM_ID, CART_ID);
        verifyNoMoreInteractions(cartItemRepository);
        verifyNoInteractions(cartRepository, cartItemReadMapper, cartItemCreateMapper);
    }

    @Test
    public void updateCartItemQuantityCartItemRepositoryThrowsException() {
        // given
        doThrow(new RuntimeException("Exception during saving Cart Item")).when(cartItemRepository).findCartItemByIdAndCartId(anyInt(), anyInt());

        // when, then
        assertThatThrownBy(() -> cartItemService.updateCartItemQuantity(CART_ID, CART_ITEM_ID, QUANTITY_TWO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exception during saving Cart Item");
    }

    // getTotalPrice
    @Test
    public void getTotalPriceSuccess() {
        // Given
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(new BigDecimal("10.00"));

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(new BigDecimal("15.00"));

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1);
        cartItem1.setQuantity(2);
        cartItem1.setProduct(product1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setId(2);
        cartItem2.setQuantity(1);
        cartItem2.setProduct(product2);

        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);

        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(cartItems);

        // When
        BigDecimal totalPrice = cartItemService.getTotalPrice(CART_ID);

        // Then
        Assertions.assertThat(totalPrice).isEqualTo(new BigDecimal("35.00"));
    }

    @Test
    public void getTotalPriceWithEmptyCart() {
        // Given
        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(new ArrayList<>());

        // When
        BigDecimal totalPrice = cartItemService.getTotalPrice(CART_ID);

        // Then
        Assertions.assertThat(totalPrice).isEqualTo(BigDecimal.ZERO);
    }
}