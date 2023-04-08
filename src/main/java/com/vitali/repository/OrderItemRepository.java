package com.vitali.repository;

import com.vitali.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    boolean existsOrderItemByProductId(Integer id);

    List<OrderItem> findOrderItemsByCartId(Long id);
}
