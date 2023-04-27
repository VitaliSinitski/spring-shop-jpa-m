package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.OrderItem;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public Integer create(CartItemCreateDto cartItemCreateDto) {
        CartItem cartItemEntity = cartItemCreateMapper.map(cartItemCreateDto);
        return cartItemRepository.save(cartItemEntity).getId();
    }

    // I use this
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


    public Optional<CartItemReadDto> findById(Integer id) {
        return cartItemRepository.findById(id)
                .map(cartItemReadMapper::map);
    }

    public List<CartItemReadDto> findAllByCartId(Integer id) {
        return cartItemRepository.findCartItemsByCartId(id)
                .stream()
                .map(cartItemReadMapper::map)
                .collect(Collectors.toList());
    }

//    public List<CartItemReadDto> findAllByOrderId(Integer id) {
//        return cartItemRepository.findCartItemsByOrderId(id).stream()
//                .map(cartItemReadMapper::map)
//                .collect(Collectors.toList());
//    }

//    @Transactional
//    public boolean delete(Integer id) {
//        Optional<CartItem> maybeCartItem = cartItemRepository.findById(id);
//        maybeCartItem.ifPresent(cartItemRepository::delete);
//        return maybeCartItem.isPresent();
//    }

    @Transactional
    public boolean delete(Integer id) {
        return cartItemRepository.findById(id)
                .map(entity -> {
                    cartItemRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean delete(Integer cartId, Integer cartItemId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        CartItem cartItemToDelete = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItemToDelete == null) {
            return false;
        }
        cart.deleteCartItem(cartItemToDelete);
        cartRepository.save(cart);
        return true;
    }


    @Transactional
    public void deleteAllByCartId(Integer id) {
        List<CartItem> cartItems = cartItemRepository.findAll();
        cartItems.removeIf(cartItem -> Objects.equals(cartItem.getCart().getId(), id));
    }


    @Transactional
    public void updateCartItemQuantity(Integer cartId, Integer cartItemId, Integer quantity) {
        cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)
                .ifPresent(cartItem -> {
                    cartItem.setQuantity(quantity);
                    cartItemRepository.save(cartItem);
                });
    }

//    @Transactional
//    public void deleteCartItem(Integer cartId, Integer cartItemId) {
//        log.info("CartService - deleteCartItem - cartItemRepository.findCartItemByIdAndCartId = {}", cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId));
//        cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)
//                .ifPresent(cartItemRepository::delete);
//    }

//    @Transactional
//    public void deleteCartItem(Integer cartId, Integer cartItemId) {
//        log.info("CartService - deleteCartItem - cartId: {}, cartItemId: {}", cartId, cartItemId);
//        cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)
//                .ifPresent(cartItem -> {
//                    log.info("CartService - deleteCartItem - found cartItem: {}", cartItem);
//                    cartItemRepository.delete(cartItem);
//                });
//    }

//    @Transactional
//    public boolean deleteCartItem(Integer cartId, Integer cartItemId) {
//        return cartItemRepository.findCartItemByIdAndCartId(cartItemId, cartId)
//                .map(entity -> {
//                    cartItemRepository.delete(entity);
//                    return true;
//                })
//                .orElse(false);
//    }

    @Transactional
    public void deleteCartItem(Integer cartItemId) {
        log.info("CartItemService - deleteCartItem - cartItemId: {}", cartItemId);

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(); //
        log.info("CartItemService - deleteCartItem - cartItem: {}", cartItem);
        cartItemRepository.deleteById(cartItemId);

//        cartItemRepository.deleteCartItemByIdAndCartId(cartItemId, cartId);
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
