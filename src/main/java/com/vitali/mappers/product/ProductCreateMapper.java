package com.vitali.mappers.product;

import com.vitali.database.entities.Product;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class ProductCreateMapper implements Mapper<ProductCreateDto, Product> {
    @Override
    public Product map(ProductCreateDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Product map(ProductCreateDto productCreateDto) {
        Product product = new Product();
        copy(productCreateDto, product);
        return product;
    }

    private void copy(ProductCreateDto object, Product product) {
        product.setName(object.getName());
        product.setDescription(object.getDescription());
        product.setPrice(object.getPrice());
        product.setQuantity(object.getQuantity());
//        product.setCategory(getCategory(object.getCategoryId()));
//        product.setProducer(getProducer(object.getProducerId()));

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> product.setImage(image.getOriginalFilename()));
    }

//    public Category getCategory(Integer id) {           // move ot ProductService
//        return Optional.ofNullable(id)
//                .flatMap(categoryRepository::findById)
//                .orElse(null);
//    }

//    public Producer getProducer(Integer id) {           // move ot ProductService
//        return Optional.ofNullable(id)
//                .flatMap(producerRepository::findById)
//                .orElse(null);
//    }
}
