package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.repositories.CartItemRepository;
import com.vitali.database.repositories.CartRepository;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.mappers.cartItem.CartItemCreateMapper;
import com.vitali.mappers.cartItem.CartItemReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartItemReadMapper cartItemReadMapper;
    private final CartItemCreateMapper cartItemCreateMapper;

    @Transactional
    public Integer create(Integer quantity, Integer productId, Integer cartId) {
        CartItemCreateDto cartItemCreateDto = CartItemCreateDto.builder()
                .quantity(quantity)
                .productId(productId)
                .cartId(cartId)
                .build();
        CartItem cartItemEntity = cartItemCreateMapper.map(cartItemCreateDto);
        return cartItemRepository.save(cartItemEntity).getId();
    }

    public List<CartItemReadDto> findAllByCartId(Integer id) {
        return cartItemRepository.findCartItemsByCartId(id)
                .stream()
                .map(cartItemReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(Integer cartId, Integer cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart with id: " + cartId + " not found"));
        CartItem cartItemToDelete = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem with id: " + cartItemId + " not found"));
        cart.deleteCartItem(cartItemToDelete);
        cartRepository.save(cart);
        return true;
    }

    @Transactional
    public boolean deleteAllByCartId(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart with id: " + cartId + " not found"));
        List<CartItem> cartItemsToDelete = cartItemRepository.findAllByCartId(cartId);
        if (cartItemsToDelete.isEmpty()) {
            return false;
        }
        for (CartItem cartItem : cartItemsToDelete) {
            cart.deleteCartItem(cartItem);
        }
        cartRepository.save(cart);
        return true;
    }

    @Transactional
    public void updateCartItemQuantity(Integer cartId, Integer cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem with id: " + cartItemId + " not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    public BigDecimal getTotalPrice(Integer cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal price = cartItem.getProduct().getPrice();
            BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
            BigDecimal itemTotalPrice = price.multiply(quantity);
            totalPrice = totalPrice.add(itemTotalPrice);
        }
        return totalPrice;
    }
}
