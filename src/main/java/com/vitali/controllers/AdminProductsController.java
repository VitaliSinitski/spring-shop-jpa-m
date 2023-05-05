package com.vitali.controllers;

import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.product.ProductFilter;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.services.CategoryService;
import com.vitali.services.ProducerService;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductsController {
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

        return "admin/products";
    }


//    @GetMapping
////    @GetMapping("/products")
//    public String findAllProducts(Model model) {
//        model.addAttribute("products", productService.findAll());
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("producers", producerService.findAll());
//        return "admin/products";
//    }


//    @GetMapping("/products/{id}")
//    @GetMapping("/{id}")
//    public String findByIdProduct(@PathVariable("id") Integer id, Model model) {
//        return productService.findById(id)
//                .map(product -> {
//                    model.addAttribute("product", product);
//                    model.addAttribute("categories", categoryService.findAll());
//                    model.addAttribute("producers", producerService.findAll());
//                    return "admin/product";
////                    return "admin/product";
//                })
//                .orElseThrow(
//                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/{id}")
    public String findByIdProduct(@PathVariable("id") Integer id, Model model) {
        ProductReadDto product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("producers", producerService.findAll());
        return "admin/product";
    }

    @GetMapping("/new") // registration
    public String newProduct(Model model,
                             @ModelAttribute("product") ProductCreateDto product) {
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("producers", producerService.findAll());
        return "admin/newProduct";
    }


    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@ModelAttribute @Validated ProductCreateDto product,
                                BindingResult bindingResult,                            // BindingResult must stay exactly after validation object!!!
                                RedirectAttributes redirectAttributes,
                                Model model) {
        log.info("AdminProductsController - createProduct - product: {}", product);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/products/new";
        } else {

            model.addAttribute("products", productService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("producers", producerService.findAll());
            productService.create(product);
//        return "redirect:/admin/products/" + productService.create(product).getId();
            return "redirect:/admin/products";
        }
    }

    //    @PostMapping("/products/{id}/update")
//    @PostMapping("/{id}/update")
//    public String updateProduct(@PathVariable("id") Integer id,
//                                @ModelAttribute @Validated ProductCreateDto product) {
//        return productService.update(id, product)
//                .map(it -> "redirect:/admin/products/{id}")
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable("id") Integer id,
                                @ModelAttribute @Validated ProductCreateDto product) {
        productService.update(id, product);
        return "redirect:/admin/products/{id}";
    }


    //    @PostMapping("/products/{id}/delete")
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/admin/products";
    }

}
