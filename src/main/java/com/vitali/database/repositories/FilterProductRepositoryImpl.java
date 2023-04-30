package com.vitali.database.repositories;


import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.QCategory;
import com.vitali.database.entities.QProducer;
import com.vitali.database.entities.QProduct;
import com.vitali.database.querydsl.QPredicates;
import com.vitali.dto.product.ProductFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vitali.database.entities.QProduct.product;

@RequiredArgsConstructor
public class FilterProductRepositoryImpl implements FilterProductRepository{
    private final EntityManager entityManager;


    @Override
    public List<Product> findAllByFilter(ProductFilter filter) {
//    public List<Product> findAllByFilter(ProductFilter filter, Sort sort) {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;
        QProducer producer = QProducer.producer;

        QPredicates builder = QPredicates.builder()
                .add(filter.getName(), product.name::containsIgnoreCase)
                .add(filter.getPrice(), product.price::eq);

        if (filter.getCategoryId() != null) {
            builder.add(filter.getCategoryId(), product.category.id::eq);
//            builder.add(filter.getCategory().getName(), product.category.name::containsIgnoreCase);
        }
        if (filter.getProducerId() != null) {
            builder.add(filter.getProducerId(), product.producer.id::eq);
//            builder.add(filter.getProducer().getName(), product.producer.name::containsIgnoreCase);
        }

        return new JPAQuery<>(entityManager)
                .select(product)
                .from(product)
                .where(builder.build())
//                .orderBy(product.category.name.asc(), product.producer.name.asc())
                .orderBy(product.producer.name.asc(), product.category.name.asc())
                .fetch();

    }

}
