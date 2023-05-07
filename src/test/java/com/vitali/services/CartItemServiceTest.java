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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.*;
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

//    private Cart CART;
//    private CartItem cartItem;

    private final CartItemReadDto CART_ITEM_READ_DTO = new CartItemReadDto();
    private final Integer CART_ID = 1;
    private final Integer CART_ITEM_ID = 2;
    private final Integer TIMES_ONE = 1;
    private final Integer QUANTITY_ONE = 1;
    private final Integer QUANTITY_TWO = 2;
    private final Integer QUANTITY_THREE = 3;
    private final Integer QUANTITY_TEN = 10;
    private final Integer PRODUCT_ID = 1;
    private final CartItemCreateDto CART_ITEM_CREATE_DTO = CartItemCreateDto.builder().quantity(QUANTITY_TWO).productId(PRODUCT_ID).cartId(CART_ID).build();
    private final Product PRODUCT = Product.builder().price(BigDecimal.valueOf(QUANTITY_TEN)).build();
    private final List<CartItemReadDto> CART_ITEM_READ_DTO_LIST = List.of(CART_ITEM_READ_DTO);
    private final Cart CART = Cart.builder().id(CART_ID).build();
//    private final Cart CART_ONE_ITEM = Cart.builder().id(CART_ID).build();
    //    private final Cart CART_ONE_ITEM = Cart.builder().id(CART_ID).cartItems(CART_ITEM_LIST_ONE).build();
//    private final Cart CART_TWO_ITEMS = Cart.builder().id(CART_ID).build();
    //    private final Cart CART_TWO_ITEMS = Cart.builder().id(CART_ID).cartItems(CART_ITEM_LIST_TWO).build();
    private final CartItem CART_ITEM_TWO = CartItem.builder().id(CART_ITEM_ID).quantity(QUANTITY_TWO).product(PRODUCT).cart(CART).build();
    private final CartItem CART_ITEM_ONE = CartItem.builder().id(CART_ITEM_ID).quantity(QUANTITY_ONE).product(PRODUCT).cart(CART).build();
    private final List<CartItem> CART_ITEM_LIST_ONE = Collections.singletonList(CART_ITEM_ONE);
    private final List<CartItem> CART_ITEM_LIST_TWO = Arrays.asList(CART_ITEM_TWO, CART_ITEM_ONE);

    @BeforeEach
    void setUp() {

//        Product product = new Product();
//        product.setPrice(BigDecimal.valueOf(QUANTITY_TEN));

//        cartItem = new CartItem();
//        cartItem.setId(CART_ITEM_ID);
//        cartItem.setQuantity(QUANTITY_TWO);
//        cartItem.setProduct(product);

//        CART = new Cart();
//        CART.setId(CART_ID);
//        CART.addCartItem(CART_ITEM_TWO);

//        CART_ITEM_READ_DTO = new CartItemReadDto();
//        CART_ITEM_CREATE_DTO = new CartItemCreateDto();
    }

    // create
    @Test
    public void createCartItem() {
        // given
        when(cartItemCreateMapper.map(CART_ITEM_CREATE_DTO)).thenReturn(CART_ITEM_ONE);
        when(cartItemRepository.save(CART_ITEM_ONE)).thenReturn(CART_ITEM_ONE);

        // when
        Integer createdCartItemID = cartItemService.create(QUANTITY_TWO, PRODUCT_ID, CART_ID);

        // then
        assertThat(createdCartItemID).isEqualTo(CART_ITEM_ONE.getId());
        verify(cartItemCreateMapper).map(CART_ITEM_CREATE_DTO);
        verify(cartItemRepository).save(CART_ITEM_ONE);
    }

    // findAllByCartId
    @Test
    public void findAllByCartId() {
        // given
        when(cartItemRepository.findCartItemsByCartId(CART_ID)).thenReturn(CART_ITEM_LIST_ONE);
        when(cartItemReadMapper.map(CART_ITEM_ONE)).thenReturn(CART_ITEM_READ_DTO);

        // when
        List<CartItemReadDto> result = cartItemService.findAllByCartId(CART_ID);

        // then
        assertThat(result).isEqualTo(CART_ITEM_READ_DTO_LIST);
        verify(cartItemRepository).findCartItemsByCartId(CART_ID);
        verify(cartItemReadMapper).map(CART_ITEM_ONE);
    }

    // delete
    @Test
    public void delete_cartItemExists_cartItemDeleted() {
        // given
        Cart cart = new Cart();
        cart.setId(CART_ID);
        cart.addCartItem(CART_ITEM_ONE);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(CART_ITEM_ID)).thenReturn(Optional.of(CART_ITEM_ONE));

//        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
//        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));


        // when
        boolean isDeleted = cartItemService.delete(CART_ID, CART_ITEM_ID);

        // then
        assertTrue(isDeleted);
        verify(cartRepository).findById(CART_ID);
        verify(cartItemRepository).findById(CART_ITEM_ID);
        verify(cartRepository).save(cart);

    }

    @Test
    public void delete_cartItemDoesNotExist_EntityNotFoundExceptionThrown() {
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
    public void delete_cartDoesNotExist_EntityNotFoundExceptionThrown() {
        // given
        when(cartRepository.findById(CART_ID)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> cartItemService.delete(CART_ID, CART_ITEM_ID));

        // then
        verify(cartRepository).findById(CART_ID);
        verifyNoMoreInteractions(cartRepository, cartItemRepository);
    }



//    @Test
//    public void deleteAllByCartId_cartExists_cartItemsDeleted() {
//        // given
//        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
//        when(cartItemRepository.findAllByCartId(cartId)).thenReturn(Arrays.asList(cartItem));
//
//        // when
//        boolean result = cartItemService.deleteAllByCartId(cartId);
//
//        // then
//        assertThat(result).isTrue();
//        assertThat(cart.getCartItems()).isEmpty();
//        verify(cartRepository, times(1)).findById(cartId);
//        verify(cartItemRepository, times(1)).findAllByCartId(cartId);
//        verify(cartRepository, times(1)).save(cart);
//
//    }

    // deleteAllByCartId
    @Test
    public void deleteAllByCartIdSuccess() {
        // given
        Cart cart = new Cart();
        cart.setId(CART_ID);

        CartItem cartItem1 = CartItem.builder().quantity(QUANTITY_TWO).cart(cart).build();
        CartItem cartItem2 = CartItem.builder().quantity(QUANTITY_ONE).cart(cart).build();
//        CartItem cartItem1 = new CartItem();
//        cartItem1.setQuantity(QUANTITY_TWO);
//        cartItem1.setCart(cart);
//        CartItem cartItem2 = new CartItem();
//        cartItem2.setQuantity(QUANTITY_ONE);
//        cartItem2.setCart(cart);

        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
//        List<CartItem> cartItems = new ArrayList<>();
//        cartItems.add(cartItem1);
//        cartItems.add(cartItem2);

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
//        Integer cartId = 1;
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
    public void deleteAllByCartIdCartNotFoundThrowEntityNotFoundException() { // ok
        // given
        when(cartRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // when and then
        assertThatThrownBy(() -> cartItemService.deleteAllByCartId(CART_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cart with id: " + CART_ID + " not found");
    }


    @Test
    public void deleteAllByCartIdThrowExceptionSaveCartFails() { // ok
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

    // тесты для метода updateCartItemQuantity()

    @Test
    public void updateCartItemQuantitySuccess() { // ok
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
    public void updateCartItemQuantityCartItemNotFoundThrowEntityNotFoundException() { // ok
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
//        Integer cartId = 1;
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
//        List<CartItem> cartItems = new ArrayList<>();
//        cartItems.add(cartItem1);
//        cartItems.add(cartItem2);

        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(cartItems);

        // When
        BigDecimal totalPrice = cartItemService.getTotalPrice(CART_ID);

        // Then
        Assertions.assertThat(totalPrice).isEqualTo(new BigDecimal("35.00"));
    }

    @Test
    public void getTotalPriceWithEmptyCart() {
        // Given
//        Integer cartId = 1;
        when(cartItemRepository.findAllByCartId(CART_ID)).thenReturn(new ArrayList<>());

        // When
        BigDecimal totalPrice = cartItemService.getTotalPrice(CART_ID);

        // Then
        Assertions.assertThat(totalPrice).isEqualTo(BigDecimal.ZERO);
    }
}