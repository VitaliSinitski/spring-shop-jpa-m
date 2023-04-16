package com.vitali.mappers.product;

import com.vitali.entities.Category;
import com.vitali.entities.Producer;
import com.vitali.mappers.Mapper;
import com.vitali.repositories.CategoryRepository;
import com.vitali.repositories.ProducerRepository;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.vitali.constants.Constants.IMAGE_FOLDER;

@Component
@RequiredArgsConstructor
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;

    @Override
    public Product map(ProductCreateDto fromObject, Product toObject) {
        return getProduct(fromObject);  // 83:00.18
    }

    @Override
    public Product map(ProductCreateDto productCreateDto) {
        return getProduct(productCreateDto);
    }

    private Product getProduct(ProductCreateDto object) {
        return Product.builder()
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .quantity(object.getQuantity())
                .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName()) // можно добавить уникальный префикс (!дублирование имен)
                .category(getCategory(object.getCategoryId()))
                .producer(getProducer(object.getProducerId()))
                .build();
    }

    public Category getCategory(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(categoryRepository::findById)
                .orElse(null);
    }

    public Producer getProducer(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(producerRepository::findById)
                .orElse(null);
    }
}
