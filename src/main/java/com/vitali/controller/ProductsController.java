package com.vitali.controller;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.service.CategoryService;
import com.vitali.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String findAllProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public String findByIdProduct(@PathVariable Integer id, Model model) {
        ProductReadDto product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "product";
    }

}
