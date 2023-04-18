package com.vitali.services;

import com.querydsl.core.types.Predicate;
import com.vitali.database.entities.QProduct;
import com.vitali.database.querydsl.QPredicates;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.database.entities.Product;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import com.vitali.database.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCreateMapper productCreateMapper;
    private final ProductReadMapper productReadMapper;
    private final ImageService imageService;



//    public List<ProductReadDto> findAll(ProductFilter filter, Sort sort) {
////        sort = Sort.by(Sort.Direction.ASC, "category.name", "producer.name");
//        return productRepository.findAllByFilter(filter, sort)
//                .stream().map(productReadMapper::map)
//                .collect(Collectors.toList());
//    }
//
//    public List<ProductReadDto> findAll(ProductFilter filter) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "category.name", "producer.name");
//        return productRepository.findAllByFilter(filter, sort)
//                .stream().map(productReadMapper::map)
//                .collect(Collectors.toList());
//    }


    public Page<ProductReadDto> findAll(ProductFilter filter, Pageable pageable) {
        QProduct product = QProduct.product;
        var predicate = QPredicates.builder()
                .add(filter.getName(), product.name::containsIgnoreCase)
                .add(filter.getPrice(), product.price::eq)
                .add(filter.getQuantity(), product.quantity::eq)
                .build();
        return productRepository.findAll(predicate, pageable)
                .map(productReadMapper::map);
    }


//    public List<ProductReadDto> findAll(ProductFilter filter) {
//        List<ProductReadDto> products = productRepository.findAllByFilter(filter)
//                .stream()
//                .map(productReadMapper::map)
//                .collect(Collectors.toList());
//        products.sort(Comparator.comparing(ProductReadDto.)
//                .thenComparing(ProductReadDto::getProducerName));



    public List<ProductReadDto> findAll() {
        return productRepository.findAll()
                .stream().map(productReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ProductReadDto> findById(Integer id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateDto productCreateDto) {
        return Optional.of(productCreateDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return productCreateMapper.map(dto);    // 97.09:50
                })
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Integer id, ProductCreateDto productCreateDto) {
        return productRepository.findById(id)
                .map(product -> {
                    uploadImage(productCreateDto.getImage());
                    return productCreateMapper.map(productCreateDto, product);
                })
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    // 97.20:20
    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findImage(Integer id) {
        return productRepository.findById(id)
                .map(Product::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
