package com.vitali.mappers.product;

import com.vitali.mappers.Mapper;
import com.vitali.repositories.CategoryRepository;
import com.vitali.repositories.ProducerRepository;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.vitali.constants.Constants.IMAGE_FOLDER;

@Component
@RequiredArgsConstructor
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;

    @Override
    public Product map(ProductCreateDto object) {
        return Product.builder()
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .quantity(object.getQuantity())
                .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName()) // можно добавить уникальный префикс (!дублирование имен)
                .category(categoryRepository.findById(object.getCategoryId())
                        .orElseThrow(IllegalArgumentException::new))
                .producer(producerRepository.findById(object.getProducerId())
                        .orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
