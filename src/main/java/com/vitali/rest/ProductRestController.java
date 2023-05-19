package com.vitali.rest;

import com.vitali.dto.PageResponse;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;

// http://localhost:8080/v3/api-docs
// http://localhost:8080/swagger-ui/index.html

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PageResponse<ProductReadDto> findAllProducts(ProductFilter filter, Pageable pageable) {
        Page<ProductReadDto> page = productService.findAll(filter, pageable);
        return PageResponse.of(page);
    }


    @GetMapping("/{id}")
    public ProductReadDto findByIdProduct(@PathVariable Integer id) {
        return productService.findById(id);
    }


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
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return productService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
