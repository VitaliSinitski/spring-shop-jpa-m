package com.vitali.database.repositories;

import com.vitali.database.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, FilterProductRepository, QuerydslPredicateExecutor<Product> {

    List<Product> findProductsByCategoryId(Integer id);

    List<Product> findProductsByProducerId(Integer id);

    //    List<Product> findAll(Predicate predicate, Pageable pageable);
    Optional<Product> findById(Integer id);
}
