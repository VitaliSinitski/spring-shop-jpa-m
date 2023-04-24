package com.vitali.controllers;

import com.vitali.converters.OrderItemCreateConverter;
import com.vitali.database.entities.Cart;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.order.OrderReadDto;
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
    private final UserRepository userRepository;
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
//                              @RequestParam String inform,
                              @RequestParam OrderStatus orderStatus,
//                              HttpServletRequest request,
                              Model model) {
        OrderReadDto order = cartService.createOrder(cartId, /*inform, */orderStatus, selectedItems);
        List<OrderItemReadDto> orderItems = orderItemService.findAllByOrderId(order.getId());
//        HttpSession session = request.getSession();
//        User currentUser = (User) session.getAttribute("currentUser");
//        UserInformation userInformation = currentUser.getUserInformation();
        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        return "redirect:/orderConfirmation";
    }

    @PostMapping("/order/confirm")
    public String orderConfirmation(@RequestParam("selectedItems") List<Integer> selectedItems,
                              @RequestParam Integer cartId,
//                              @RequestParam String inform,
                              @RequestParam OrderStatus orderStatus) {

        cartService.createOrder(cartId, /*inform, */orderStatus, selectedItems);
        return "redirect:/orderConfirmation";
    }

    public void getUserInformation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        Cart userCart = (Cart) session.getAttribute("userCart");
        Integer cartId = (Integer) session.getAttribute("cartId");
        Integer userId = currentUser.getId();
        UserInformation userInformation = currentUser.getUserInformation();
    }


}
