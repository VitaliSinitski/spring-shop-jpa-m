package com.vitali.database.repositories;

import com.vitali.database.entities.Product;
import com.vitali.dto.product.ProductFilter;

import java.util.List;

public interface FilterProductRepository {
    List<Product> findAllByFilter(ProductFilter productFilter);
}
