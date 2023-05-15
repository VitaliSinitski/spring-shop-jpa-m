package com.vitali.controllers;

import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.exception.OutOfStockException;
import com.vitali.services.CategoryService;
import com.vitali.services.ProducerService;
import com.vitali.services.ProductService;
import com.vitali.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProducerService producerService;

    @GetMapping
    public String findAllProducts(Model model, ProductFilter filter,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "name") String sortField,
                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                  @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
                                  @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
                                  UriComponentsBuilder uriBuilder) {
        filter.setSortField(sortField);
        filter.setSortDirection(sortDirection);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);

        model.addAttribute("page", productPage);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("filter", filter);
        model.addAttribute("producers", producerService.findAll());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        return "products";
    }

    @GetMapping("/{id}")
    public String findByIdProduct(@PathVariable Integer id,
                                  Model model,
                                  HttpSession session) {
        session.setAttribute("productId", id);
        ProductReadDto product = productService.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated ProductCreateDto product) {
        return "redirect:/products/" + productService.create(product).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated ProductCreateDto product) {
        productService.update(id, product);
        return "redirect:/products/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products";
    }
}
