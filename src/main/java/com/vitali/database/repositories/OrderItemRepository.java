package com.vitali.database.repositories;

import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    boolean existsOrderItemByProductId(Integer id);
    List<OrderItem> findOrderItemsByCartId(Integer id);
    List<OrderItem> findOrderItemsByOrderId(Integer id);
    List<OrderItem> findAllByIdIn(List<Integer> ids);
    List<OrderItem> findAllByCartId(Integer id);
    void deleteAllByCartId(Integer id);
    List<OrderItem> findByOrder(Order order);
}
