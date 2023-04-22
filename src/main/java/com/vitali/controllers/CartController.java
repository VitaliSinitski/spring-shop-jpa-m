package com.vitali.controllers;

import com.vitali.converters.OrderItemCreateConverter;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.services.CartService;
import com.vitali.services.OrderItemService;
import com.vitali.services.ProductService;
import com.vitali.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        HttpSession session = request.getSession();
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
        orderItemCreateDto.setCartId(cartId);
        orderItemService.create(orderItemCreateDto);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        List<OrderItemReadDto> orderItems = orderItemService.findAllByCartId(id);
        model.addAttribute("orderItems", orderItems);
        return "cart";
    }

//    @PostMapping("/order")
//    public String createProduct(@ModelAttribute ProductCreateDto product,
//                                HttpServletRequest request,
//                                Model model) {
//        int cartId = Integer.parseInt(request.getParameter("cartId"));
//        log.info("OrderController - cartId: {}", cartId);
//        return "admin/products";
//    }

    @PostMapping("/order")
    public String createOrder(@RequestParam("selectedItems") List<Integer> selectedItems,
                              @RequestParam Integer cartId,
                              @RequestParam String inform,
                              @RequestParam OrderStatus orderStatus) {
        log.info("OrderController - cartId: {}", cartId);
        log.info("OrderController - selectedItems: {}", selectedItems);
        log.info("OrderController - inform: {}", inform);
        log.info("OrderController - orderStatus: {}", orderStatus);
        cartService.createOrder(cartId, inform, orderStatus, selectedItems);
        return "redirect:/order-confirmation";
    }


}
