package com.vitali.database.repositories;

import com.vitali.database.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findFirstByOrderByCreatedDateDesc();
//    Order findTopByCreatedDate();
}
