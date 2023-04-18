package com.vitali.database.repositories;


import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.QProduct;
import com.vitali.database.querydsl.QPredicates;
import com.vitali.dto.product.ProductFilter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vitali.database.entities.QProduct.product;

@RequiredArgsConstructor
public class FilterProductRepositoryImpl implements FilterProductRepository{
    private final EntityManager entityManager;
//    @Override
//    public List<Product> findAllByFilter(ProductFilter filter) {
//        Predicate predicate = QPredicates.builder()
//                .add(filter.getName(), product.name::containsIgnoreCase)
//                .add(filter.getPrice(), product.price::eq)
//                .add(filter.getQuantity(), product.quantity::eq)
////                .add(filter.getCategory().getName(), product.category.name::eq)
////                .add(filter.getProducer().getName(), product.producer.name::eq)
//                .build();
//
//        return new JPAQuery<Product>(entityManager)
//                .select(product)
//                .from(product)
//                .where(predicate)
//                .fetch();
//    }
    @Override
    public List<Product> findAllByFilter(ProductFilter filter) {
        QProduct product = QProduct.product;
        QPredicates builder = QPredicates.builder()
                .add(filter.getName(), product.name::containsIgnoreCase)
                .add(filter.getPrice(), product.price::eq)
                .add(filter.getQuantity(), product.quantity::eq);

        if (filter.getCategory() != null) {
            builder.add(filter.getCategory().getId(), product.category.id::eq);
            builder.add(filter.getCategory().getName(), product.category.name::containsIgnoreCase);
        }
        if (filter.getProducer() != null) {
            builder.add(filter.getProducer().getId(), product.producer.id::eq);
            builder.add(filter.getProducer().getName(), product.producer.name::containsIgnoreCase);
        }

        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        query.select(product)
                .from(product)
                .where(builder.build())
                .orderBy(product.category.name.asc(), product.producer.name.asc());

        return query.fetch();
    }

}
