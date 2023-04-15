package com.vitali.controllers;

import com.vitali.converters.OrderItemCreateConverter;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.services.CartService;
import com.vitali.services.OrderItemService;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final OrderItemCreateConverter orderItemCreateConverter;

    @PostMapping("/new")
    public String addToCart(HttpServletRequest request, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        OrderItemCreateDto orderItemCreateDto = orderItemCreateConverter.convert(request);
        // TODO: 11.04.2023  orderItemCreateDto.setCartId(cartId);
        orderItemService.create(orderItemCreateDto);
        return "redirect:/products";
    }


//    @PostMapping("/cart/new")
//    public String addToCart(@RequestParam("quantity") int quantity, @RequestParam("productId") Long productId, HttpSession session) {
//        // your code here
//    }


//    @PostMapping("/new")
//    public String addToCart(HttpServletRequest request, Model entities) {
//        OrderItemCreateDto orderItemCreateDto = orderItemCreateConverter.convert(request);
//        orderItemService.create(orderItemCreateDto);
//        Long productId = orderItemCreateDto.getProductId();
//        Integer quantity = orderItemCreateDto.getQuantity();
//        entities.addAttribute("productId", productId);
//        entities.addAttribute("quantity", quantity);
//        return "redirect:/product/{productId}";
//    }



}
