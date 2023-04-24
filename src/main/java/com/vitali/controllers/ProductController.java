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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                                  UriComponentsBuilder uriBuilder) {

        // Update filter with sort parameters
        filter.setSortField(sortField);
        filter.setSortDirection(sortDirection);

        // Create page request with pagination and sort parameters
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        // Get page of products
        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);

        // Add page parameters to model
        model.addAttribute("page", productPage);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("filter", filter);
        model.addAttribute("producers", producerService.findAll());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        // Add sort and size parameters to pagination links
        String url = uriBuilder.replaceQueryParam("sortField", sortField)
                .replaceQueryParam("sortDirection", sortDirection)
                .replaceQueryParam("size", size)
                .toUriString();
        model.addAttribute("url", url);

        return "products";
    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter,
//                                  @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size,
//                                  @RequestParam(defaultValue = "name") String sortField,
//                                  @RequestParam(defaultValue = "asc") String sortDirection,
//                                  UriComponentsBuilder uriBuilder) {
//
//        // Update filter with sort parameters
//        filter.setSortField(sortField);
//        filter.setSortDirection(sortDirection);
//
//        // Create page request with pagination and sort parameters
//        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
//
//        // Get page of products
//        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);
//
//        // Add page parameters to model
//        model.addAttribute("page", productPage);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("filter", filter);
//        model.addAttribute("producers", producerService.findAll());
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDirection", sortDirection);
//
//        // Add sort and size parameters to pagination links
//        String url = uriBuilder.replaceQueryParam("sortField", sortField)
//                .replaceQueryParam("sortDirection", sortDirection)
//                .replaceQueryParam("size", size)
//                .toUriString();
//        model.addAttribute("url", url);
//
//        return "products";
//    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter,        // deleted 04.24.12.47
//                                  @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size,
//                                  @RequestParam(defaultValue = "name") String sortField,
//                                  @RequestParam(defaultValue = "asc") String sortDirection,
//                                  UriComponentsBuilder uriBuilder) {
//
//        // Update filter with sort parameters
//        filter.setSortField(sortField);
//        filter.setSortDirection(sortDirection);
//
//        // Create page request with pagination and sort parameters
//        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
//
//        // Get page of products
//        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);
//
//        // Add page parameters to model
//        model.addAttribute("page", productPage);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("filter", filter);
//        model.addAttribute("producers", producerService.findAll());
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDirection", sortDirection);
//
//        // Add sort parameters to pagination links
//        String url = uriBuilder.replaceQueryParam("sortField", sortField)
//                .replaceQueryParam("sortDirection", sortDirection)
//                .toUriString();
//        model.addAttribute("url", url);
//
//        return "products";
//    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter,            // deleted 04.24.12.30
//                                  @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size,
//                                  @RequestParam(defaultValue = "name") String sortField,
//                                  @RequestParam(defaultValue = "asc") String sortDirection) {
//
//        // Update filter with sort parameters
//        filter.setSortField(sortField);
//        filter.setSortDirection(sortDirection);
//
//        // Create page request with pagination and sort parameters
//        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
//
//        // Get page of products
//        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);
//
//        // Add page parameters to model
//        model.addAttribute("page", productPage);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("filter", filter);
//        model.addAttribute("producers", producerService.findAll());
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDirection", sortDirection);
//
//        return "products";
//    }

//    private List<Integer> getPaginationNumbers(Page<Product> page) {
//        int current = page.getNumber() + 1;
//        int begin = Math.max(1, current - 5);
//        int end = Math.min(begin + 10, page.getTotalPages());
//        return IntStream.range(begin, end)
//                .boxed()
//                .collect(Collectors.toList());
//    }



//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter,
//                                  @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size,
//                                  @RequestParam(defaultValue = "name") String sortField,
//                                  @RequestParam(defaultValue = "asc") String sortDirection) {
//
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
//        PageRequest pageable = PageRequest.of(page, size, sort);
//        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);
//
//        model.addAttribute("page", productPage);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("filter", filter);
//        model.addAttribute("producers", producerService.findAll());
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDirection", sortDirection);
//        return "products";
//    }


//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter, @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size,
//                                  @RequestParam(defaultValue = "name") String sortField,
//                                  @RequestParam(defaultValue = "asc") String sortDirection) {
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
//        PageRequest pageable = PageRequest.of(page, size, sort);
//        Page<ProductReadDto> productPage = productService.findAll(filter, pageable);
//
//        model.addAttribute("page", productPage);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("filter", filter);
//        model.addAttribute("producers", producerService.findAll());
//        return "products";
//    }



//    @GetMapping
//    public String findAllProducts(Model model, ProductFilter filter, Pageable pageable) { // deleted 04.24.00.15
//        Page<ProductReadDto> page = productService.findAll(filter, pageable);
//        model.addAttribute("page", page);
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("filter", filter);
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
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated ProductCreateDto product) {
        return "redirect:/products/" + productService.create(product).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated ProductCreateDto product) {
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
