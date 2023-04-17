package com.vitali.mappers.product;

import com.vitali.database.entities.Category;
import com.vitali.database.entities.Producer;
import com.vitali.mappers.Mapper;
import com.vitali.database.repositories.CategoryRepository;
import com.vitali.database.repositories.ProducerRepository;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.database.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;

    @Override
    public Product map(ProductCreateDto fromObject, Product toObject) {
//        return getProduct(fromObject);  // 83.00.18 97.09.00
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Product map(ProductCreateDto productCreateDto) {
//        return getProduct(productCreateDto);
        Product product = new Product();
        copy(productCreateDto, product);
        return product;
    }

//    private Product getProduct(ProductCreateDto object) {
//        return Product.builder()
//                .name(object.getName())
//                .description(object.getDescription())
//                .price(object.getPrice())
//                .quantity(object.getQuantity())
////                .image(Optional.ofNullable(object.getImage()).filter(MultipartFile::isEmpty).ifPresent(image -> object.getImage());)
////                .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName()) // можно добавить уникальный префикс (!дублирование имен)
//                .category(getCategory(object.getCategoryId()))
//                .producer(getProducer(object.getProducerId()))
//                .build();
//    }

    private void copy(ProductCreateDto object, Product product) {
        product.setName(object.getName());
        product.setDescription(object.getDescription());
        product.setPrice(object.getPrice());
        product.setQuantity(object.getQuantity());
        product.setCategory(getCategory(object.getCategoryId()));
        product.setProducer(getProducer(object.getProducerId()));

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> product.setImage(image.getOriginalFilename()));
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
