package com.vitali.repository;

import com.vitali.constants.OrderStatus;
import com.vitali.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findFirstByOrderByCreatedDateDesc();
//    Order findTopByCreatedDate();
//    void setOrderStatus(OrderStatus orderStatus);
    void updateOrderStatus(OrderStatus orderStatus);
}
