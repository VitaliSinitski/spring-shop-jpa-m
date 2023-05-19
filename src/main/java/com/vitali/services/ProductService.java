package com.vitali.services;

import com.vitali.database.entities.*;
import com.vitali.database.querydsl.QPredicates;
import com.vitali.database.repositories.CategoryRepository;
import com.vitali.database.repositories.ProducerRepository;
import com.vitali.database.repositories.ProductRepository;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.exception.NotEnoughStockException;
import com.vitali.exception.OutOfStockException;
import com.vitali.mappers.product.ProductCreateMapper;
import com.vitali.mappers.product.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final ProductCreateMapper productCreateMapper;
    private final ProductReadMapper productReadMapper;
    private final ImageService imageService;

    public Page<ProductReadDto> findAll(ProductFilter filter, Pageable pageable) {
        QProduct product = QProduct.product;
        var predicate = QPredicates.builder()
                .add(filter.getName(), product.name::containsIgnoreCase)
                .add(filter.getCategoryId(), product.category.id::eq)
                .add(filter.getProducerId(), product.producer.id::eq)
                .add(filter.getPrice(), product.price::eq)
                .add(filter.getMinPrice(), product.price::goe)
                .add(filter.getMaxPrice(), product.price::loe)
                .build();
        return productRepository.findAll(predicate, pageable)
                .map(productReadMapper::map);
    }

    public List<ProductReadDto> findAll() {
        return productRepository.findAll()
                .stream().map(productReadMapper::map)
                .collect(Collectors.toList());
    }

    public ProductReadDto findById(Integer id) {
        return productRepository.findById(id)
                .map(productReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found"));
    }

    @Transactional
    public ProductReadDto create(ProductCreateDto productCreateDto) {
        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Producer producer = producerRepository.findById(productCreateDto.getProducerId())
                .orElseThrow(() -> new EntityNotFoundException("Producer not found"));
        Product product = Optional.of(productCreateDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return productCreateMapper.map(dto);
                }).orElseThrow();
        product.setCategory(category);
        product.setProducer(producer);
        Product savedProduct = productRepository.save(product);
        return productReadMapper.map(savedProduct);
    }

    @Transactional
    public ProductReadDto update(Integer id, ProductCreateDto productCreateDto) {
        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Producer producer = producerRepository.findById(productCreateDto.getProducerId())
                .orElseThrow(() -> new EntityNotFoundException("Producer not found"));
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found"));
        Product mappedProduct = productCreateMapper.map(productCreateDto, product);
        mappedProduct.setCategory(category);
        mappedProduct.setProducer(producer);
        Product savedProduct = productRepository.saveAndFlush(mappedProduct);
        return productReadMapper.map(savedProduct);
    }

    @Transactional
    public boolean updateProductQuantityByCartItem(CartItem cartItem) {
        Product product = cartItem.getProduct();
        Integer productId = cartItem.getProduct().getId();

        if (product.getQuantity() == null || product.getQuantity() == 0) {
            throw new OutOfStockException("Product: " + product.getName() + " is out of stock.");
        }
        int restStock = product.getQuantity() - cartItem.getQuantity();
        if (restStock < 0) {
            throw new NotEnoughStockException("There is not enough stock for " + cartItem.getProduct().getName()
                                              + ". Current stock quantity: " + product.getQuantity()
                                              + ", you ordered: " + cartItem.getQuantity() + ".");
        }

        product.setQuantity(restStock);
        productRepository.save(product);
        return true;
    }

    @Transactional
    public boolean delete(Integer id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found"));
    }

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
