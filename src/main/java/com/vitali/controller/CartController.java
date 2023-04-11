package com.vitali.controller;

import com.vitali.converter.OrderItemCreateConverter;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.service.CartService;
import com.vitali.service.OrderItemService;
import com.vitali.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/{id}")
    public String addToCart(HttpServletRequest request, Model model) {
        OrderItemCreateDto orderItemCreateDto = orderItemCreateConverter.convert(request);
        // TODO: 11.04.2023  orderItemCreateDto.setCartId(cartId);
        orderItemService.create(orderItemCreateDto);
        return "products";
    }
}
