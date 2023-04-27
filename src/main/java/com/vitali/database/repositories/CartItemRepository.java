package com.vitali.database.repositories;

import com.vitali.database.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    boolean existsCartItemByProductId(Integer id);

    List<CartItem> findCartItemsByCartId(Integer id);
//    Optional<CartItem> findCartItemByCartIdAndCartItemId(Integer cartId, Integer cartItemId);
    Optional<CartItem> findCartItemByIdAndCartId(Integer cartItemId, Integer cartId);

//    List<CartItem> findCartItemsByOrderId(Integer id);
    List<CartItem> findAllByIdIn(List<Integer> ids);
    List<CartItem> findAllByCart_Id(Integer id);
    List<CartItem> findAllByCartId(Integer id);
    void deleteCartItemByIdAndCartId(Integer cartItemId, Integer cartId);
    boolean deleteAllByCart_Id(Integer id);
}
