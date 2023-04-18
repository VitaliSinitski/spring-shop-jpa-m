package com.vitali.database.repositories;

import com.vitali.database.entities.Product;
import com.vitali.dto.product.ProductFilter;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface FilterProductRepository {
//    List<Product> findAllByFilter(ProductFilter productFilter, Sort sort);
    List<Product> findAllByFilter(ProductFilter productFilter);
}
