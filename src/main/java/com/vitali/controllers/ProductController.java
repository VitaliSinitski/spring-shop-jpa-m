package com.vitali.controllers;

import com.vitali.database.entities.Product;
import com.vitali.dto.PageResponse;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.services.CategoryService;
import com.vitali.services.ProducerService;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProducerService producerService;
//    private final ProductFilter productFilter;

//    @GetMapping("/products")
//    public String showProductsPage(@ModelAttribute("productFilter") ProductFilter productFilter, Model model) {
//        List<ProductReadDto> products = productService.findAll(productFilter);
//        model.addAttribute("products", products);
//        return "products";
//    }
//
//    @ModelAttribute("productFilter")
//    public ProductFilter getProductFilter() {
//        return new ProductFilter();
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Sort.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                if ("categoryNameAsc".equals(text)) {
//                    setValue(Sort.by(Sort.Direction.ASC, "category.name"));
//                } else if ("categoryNameDesc".equals(text)) {
//                    setValue(Sort.by(Sort.Direction.DESC, "category.name"));
//                } else if ("priceAsc".equals(text)) {
//                    setValue(Sort.by(Sort.Direction.ASC, "price"));
//                } else if ("priceDesc".equals(text)) {
//                    setValue(Sort.by(Sort.Direction.DESC, "price"));
//                } else {
//                    throw new IllegalArgumentException("Invalid sort parameter: " + text);
//                }
//            }
//        });
//    }

//
//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "category.name", "producer.name");
//        model.addAttribute("products", productService.findAll(filter, sort));
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("producers", producerService.findAll());
//        return "products";
//    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter, @RequestParam(defaultValue = "category.name asc") String sort) {
//        if (sort.equals("categoryNameAsc")) {
//            model.addAttribute("products", productService.findAll(filter, Sort.by("category.name")));
//        } else if (sort.equals("producerNameAsc")) {
//            model.addAttribute("products", productService.findAll(filter, Sort.by("producer.name")));
//        } else {
//            model.addAttribute("products", productService.findAll(filter, Sort.by("category.name", "producer.name")));
//        }
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("producers", producerService.findAll());
//        return "products";
//    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "category.name", "producer.name");
//        model.addAttribute("products", productService.findAll(filter, sort));
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("producers", producerService.findAll());
//        return "products";
//    }

    @GetMapping
    public String findAllProducts(Model model, ProductFilter filter, Pageable pageable) {
        Page<ProductReadDto> page = productService.findAll(filter, pageable);
        model.addAttribute("products", PageResponse.of(page));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("filter", filter);
        model.addAttribute("producers", producerService.findAll());
        return "products";
    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter) {
//
//        List<ProductReadDto> products = productService.findAll(filter)
//                .stream()
//                .map(productReadMapper::map)
//                .collect(Collectors.toList());
//        products.sort(Comparator.comparing(ProductReadDto::getCategoryName)
//                .thenComparing(ProductReadDto::getProducerName));
//
//        model.addAttribute("products", products);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("producers", producerService.findAll());
//        return "products";
//    }

    @GetMapping("/{id}")
    public String findByIdProduct(@PathVariable Integer id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("categories", categoryService.findAll());
                    return "product";
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute ProductCreateDto product) {
        return "redirect:/products/" + productService.create(product).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute ProductCreateDto product) {
        return productService.update(id, product)
                .map(it -> "redirect:/products/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products";
    }

}