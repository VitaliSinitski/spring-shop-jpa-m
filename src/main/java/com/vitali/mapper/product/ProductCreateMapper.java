package com.vitali.mapper.product;

import com.vitali.mapper.Mapper;
import com.vitali.repository.CategoryRepository;
import com.vitali.repository.ProducerRepository;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
