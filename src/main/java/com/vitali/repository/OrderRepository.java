package com.vitali.repository;

import com.vitali.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findFirstByOrderByCreatedDateDesc();
//    Order findTopByCreatedDate();
}
