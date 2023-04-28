package com.vitali.controllers;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.dto.order.OrderCreateDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.product.ProductCreateDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.services.CartItemService;
import com.vitali.services.OrderItemService;
import com.vitali.services.OrderService;
import com.vitali.services.UserInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
//@SessionAttributes({"userId", "orderId", "cartId", "userCart"})
public class AdminOrdersController {
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final UserInformationService userInformationService;


    @GetMapping
    public String findAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "/admin/orders";
    }

    @GetMapping("/{id}")
    public String findByIdOrder(@PathVariable("id") Integer id, Model model) {
        UserInformationReadDto userInformation = userInformationService.findUserInformationByOrderId(id);
        log.info("AdminOrdersController - findByIdOrder - userInformation: {}", userInformation);
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("orderStatuses", OrderStatus.values());
                    model.addAttribute("userInformation", userInformation);
                    model.addAttribute("order", order);
                    return "admin/order";
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable("id") Integer id,
                                @ModelAttribute OrderCreateDto order) {
        return orderService.update(id, order)
                .map(it -> "redirect:/admin/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
