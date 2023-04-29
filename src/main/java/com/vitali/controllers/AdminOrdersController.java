package com.vitali.controllers;

import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.dto.order.OrderReadDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrdersController {
    private final OrderService orderService;
    private final UserInformationService userInformationService;
    private final OrderItemService orderItemService;


    @GetMapping
    public String findAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "/admin/orders";
    }

    @GetMapping("/{id}")
    public String findByIdOrder(@PathVariable("id") Integer orderId, Model model) {
        UserInformationReadDto userInformation = userInformationService.findUserInformationByOrderId(orderId);
        log.info("AdminOrdersController - findByIdOrder - userInformation: {}", userInformation);
        return orderService.findById(orderId)
                .map(order -> {
                    model.addAttribute("totalPrice", orderItemService.getTotalPrice(orderId));
                    model.addAttribute("orderStatuses", OrderStatus.values());
                    model.addAttribute("userInformation", userInformation);
                    model.addAttribute("order", order);
                    return "admin/order";
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @PostMapping("/{id}/update")
//    public String updateOrder(@PathVariable("id") Integer orderId,
//                              @ModelAttribute OrderStatus orderStatus,
//                              Model model) {
//        UserInformationReadDto userInformation = userInformationService.findUserInformationByOrderId(orderId);
//        orderService.updateOrderStatus(orderStatus, orderId);
//        model.addAttribute("orderStatuses", OrderStatus.values());
//        model.addAttribute("userInformation", userInformation);
//        model.addAttribute("order", orderService.findById(orderId));
//        return "redirect:/admin/orders/{id}";
//
////                .map(it -> "redirect:/admin/orders/{id}")
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
////    }
//    }

//    @PostMapping("/{id}/update")
//    public String updateOrder(@PathVariable("id") Integer orderId,
//                              @ModelAttribute OrderStatus orderStatus,
//                              Model model) {
//        UserInformationReadDto userInformation = userInformationService.findUserInformationByOrderId(orderId);
//        orderService.updateOrderStatus(orderStatus, orderId);
//        OrderReadDto updatedOrder = orderService.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        model.addAttribute("orderStatuses", OrderStatus.values());
//        model.addAttribute("userInformation", userInformation);
//        model.addAttribute("order", updatedOrder);
//        return "redirect:/admin/orders/{id}";
//    }

    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable("id") Integer orderId,
                              @ModelAttribute OrderStatus orderStatus,
                              Model model) {
        UserInformationReadDto userInformation = userInformationService.findUserInformationByOrderId(orderId);
        orderService.updateOrderStatus(orderStatus, orderId);
        OrderReadDto order = orderService.findById(orderId).orElse(null);
        if (order == null) {
            log.info("AdminOrdersController - updateOrder - order == null");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("order", order);
        return "redirect:/admin/orders/{id}";
    }


}