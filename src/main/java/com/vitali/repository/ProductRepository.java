package com.vitali.repository;

import com.vitali.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findProductsByCategoryId(Integer id);
    List<Product> findProductsByProducerId(Integer id);
}
