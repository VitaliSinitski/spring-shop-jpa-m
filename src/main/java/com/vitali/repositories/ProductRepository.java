package com.vitali.repositories;

import com.vitali.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findProductsByCategoryId(Integer id);
    List<Product> findProductsByProducerId(Integer id);
}
