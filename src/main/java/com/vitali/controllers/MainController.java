package com.vitali.controllers;

import com.vitali.services.CategoryService;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/", "/index", "/main"})
public class MainController {
    private final ProductService productService;
    private final CategoryService categoryService;
    @RequestMapping
    public String main(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "redirect:/products";
    }
}
