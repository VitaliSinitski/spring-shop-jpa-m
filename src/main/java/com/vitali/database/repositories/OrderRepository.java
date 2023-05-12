package com.vitali.database.repositories;

import com.vitali.database.entities.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findFirstByOrderByCreatedDateDesc();
    List<Order> findAllByUserId(Integer Id);
//    Order findTopByCreatedDate();
}
