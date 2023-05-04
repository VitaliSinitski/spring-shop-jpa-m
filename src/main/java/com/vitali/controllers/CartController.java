package com.vitali.controllers;

import com.vitali.aop.ShoppingCartAspect;
import com.vitali.converters.CartItemCreateConverter;

import com.vitali.dto.cartItem.CartItemReadDto;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.services.CartItemService;
import com.vitali.services.CartService;

import com.vitali.services.OrderItemService;
import com.vitali.services.ProductService;
import com.vitali.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final ShoppingCartAspect shoppingCartAspect;

    @GetMapping("/{id}")
    public String showCart(@PathVariable Integer id,
                           Model model, HttpSession session,
                           RedirectAttributes redirectAttributes) {

        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(id);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartItemService.getTotalPrice(id));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer quantity,
                            @RequestParam Integer productId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        ProductReadDto product = productService.findById(productId);
        if (product == null) {
            return "redirect:/products";
        }
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);

        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(cartId);
        for (CartItemReadDto cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)) {
                redirectAttributes.addFlashAttribute("message", "The product '" + product.getName() + "' is already in the cart.");
                return "redirect:/cart/" + cartId;
            }
        }

        cartItemService.create(quantity, productId, cartId);
        return "redirect:/products";
    }


    @PostMapping("/update")
    public String updateCart(@RequestParam Integer cartItemId,
                             @RequestParam Integer quantity,
                             HttpSession session,
                             Model model) {
        if (quantity <= 0) {
            return "redirect:/cart/remove?id=" + cartItemId;
        }
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
        cartItemService.updateCartItemQuantity(cartId, cartItemId, quantity);
        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(cartId);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartItemService.getTotalPrice(cartId));
        return "redirect:/cart/" + cartId;
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer cartItemId,
                                 HttpSession session,
                                 Model model) {
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);

        if (!cartItemService.delete(cartId, cartItemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(cartId);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartItemService.getTotalPrice(cartId));
        return "redirect:/cart/" + cartId;
    }
}