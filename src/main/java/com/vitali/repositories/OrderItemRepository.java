package com.vitali.repositories;

import com.vitali.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    boolean existsOrderItemByProductId(Integer id);

    List<OrderItem> findOrderItemsByCartId(Long id);

//    List<OrderItem> findOrderItemsByIdExists(List<String> ids);
    List<OrderItem> findAllByIdIn(List<Integer> ids);
}