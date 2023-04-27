package com.vitali.controllers;

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
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartItemCreateConverter cartItemCreateConverter;
    private final OrderItemService orderItemService;

    //    @GetMapping
    @GetMapping("/{id}")
    public String showCart(@PathVariable Integer id,
                           Model model, HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("userCart");
//        if (cart == null) {
//            User user = (User) session.getAttribute("currentUser");
//            if (user == null) {
//                return "redirect:/login";
//            }
//        }
//        Object cartIdObject = session.getAttribute("cartId");
//        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
//        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(cartId);
        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(id);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartItemService.getTotalPrice(id));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer quantity,
                            @RequestParam Integer productId,
                            HttpSession session) {
        ProductReadDto product = productService.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);


/*        log.info("CartController - addToCart - cartId: {}", cartId);
        log.info("CartController - addToCart - delete - start");
        orderItemService.delete(1);
//        orderItemService.deleteAllByCartId(cartId);
        log.info("CartController - addToCart - delete - finish");*/



        cartItemService.create(quantity, productId, cartId);
        return "redirect:/products";
    }


    @PostMapping("/update")
    public String updateCart(@RequestParam Integer cartItemId,
                             @RequestParam Integer quantity,
                             HttpSession session,
                             Model model) {
        log.info("CartController - updateCart - cartItemId: {}", cartItemId);
        log.info("CartController - updateCart - quantity: {}", quantity);
        if (quantity <= 0) {
            return "redirect:/cart/remove?id=" + cartItemId;
        }
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
        log.info("CartController - updateCart - cartId: {}", cartId);
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
//        cartItemService.delete(cartId, cartItemId);
//        log.info("CartController - removeFromCart - deleteCartItem: {}", deleteCartItem);
        List<CartItemReadDto> cartItems = cartItemService.findAllByCartId(cartId);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartItemService.getTotalPrice(cartId));
        return "redirect:/cart/" + cartId;
    }

}