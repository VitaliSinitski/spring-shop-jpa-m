package com.vitali.rest;

import com.vitali.dto.PageResponse;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.services.CategoryService;
import com.vitali.services.ProducerService;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.*;

// http://localhost:8080/v3/api-docs
// http://localhost:8080/swagger-ui/index.html

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProducerService producerService;


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PageResponse<ProductReadDto> findAllProducts(ProductFilter filter, Pageable pageable) {
        Page<ProductReadDto> page = productService.findAll(filter, pageable);
        return PageResponse.of(page);
    }


    @GetMapping("/{id}")
    public ProductReadDto findByIdProduct(@PathVariable Integer id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Version 01
//    @GetMapping(value = "/{id}/image")
//    public byte[] findImage(@PathVariable("id") Integer id) {
//        return productService.findImage(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }


    // Version 02
    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable("id") Integer id) {
        return productService.findImage(id)
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReadDto create(@Validated @RequestBody ProductCreateDto product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ProductReadDto update(@PathVariable("id") Integer id,
                                 @Validated @RequestBody ProductCreateDto product) {
        return productService.update(id, product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Integer id) {
//        if (!productService.delete(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return productService.delete(id)
                ? noContent().build()
                : notFound().build();
    }

}
