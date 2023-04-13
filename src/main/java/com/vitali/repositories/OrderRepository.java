package com.vitali.repositories;

import com.vitali.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findFirstByOrderByCreatedDateDesc();
//    Order findTopByCreatedDate();
}
