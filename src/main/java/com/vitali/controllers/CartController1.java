package com.vitali.controllers;

import com.vitali.converters.CartItemCreateConverter;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.dto.order.OrderCreateDto;
import com.vitali.services.CartItemService;
import com.vitali.services.OrderService;
import com.vitali.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
//@Controller
@RequiredArgsConstructor
//@RequestMapping("/cart")
public class CartController1 {
    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final CartItemCreateConverter cartItemCreateConverter;
//    private final OrderCreateConverter orderCreateConverter;

//    @PostMapping("/new")
    public String addToCart(HttpServletRequest request, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        CartItemCreateDto cartItemCreateDto = cartItemCreateConverter.convert(request);
        HttpSession session = request.getSession();
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
        cartItemCreateDto.setCartId(cartId);
        cartItemService.create(cartItemCreateDto);
        return "redirect:/products";
    }

//    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(id);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

//    @PostMapping("/delete")
    public String deleteCartItem(@RequestParam Integer itemId,
                                 Integer cartId,
                                 RedirectAttributes redirectAttributes) {
        cartItemService.delete(itemId);
        redirectAttributes.addFlashAttribute("cartId", cartId);
        return "redirect:/cart/" + cartId;
    }

//    @PostMapping("/deleteAll")
    public String deleteAllCartItems(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cartId = ParameterUtil.getIntegerFromObject(session.getAttribute("cartId"));
        cartItemService.deleteAllByCartId(cartId);
        return "redirect:/products";
    }

//    @PostMapping("/order")
    public String createOrder(HttpServletRequest request,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        HttpSession session = request.getSession();
        Integer userId = ParameterUtil.getIntegerFromObject(session.getAttribute("userId"));
        List<Integer> selectedItems = ParameterUtil.getIntegerListFromObject(request.getParameterValues("selectedItems"));
        if (selectedItems == null || selectedItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select at least one item to create an order.");
            return "redirect:/cart/" + session.getAttribute("cartId");
        }
/*        OrderCreateDto orderCreateDto = orderCreateConverter.convert(request);
        orderCreateDto.setUserId(userId);
        orderCreateDto.setOrderItems(selectedItems);
        orderService.create(orderCreateDto);
        cartItemService.deleteAllByCartId((Integer) session.getAttribute("cartId"));
        redirectAttributes.addFlashAttribute("message", "Order created successfully.");*/
        return "redirect:/products";
    }
}

