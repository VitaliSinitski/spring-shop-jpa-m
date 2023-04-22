package com.vitali.database.repositories;

import com.vitali.database.entities.Cart;
import com.vitali.dto.cart.CartReadDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
//    Optional<CartReadDto> findById(Integer id);
}
