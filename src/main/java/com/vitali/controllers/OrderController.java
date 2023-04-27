package com.vitali.controllers;

import com.vitali.converters.OrderItemCreateConverter;
import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Order;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.OrderItemRepository;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.services.CartItemService;
import com.vitali.services.CartService;
import com.vitali.services.OrderItemService;
import com.vitali.services.OrderService;
import com.vitali.services.ProductService;
import com.vitali.services.UserInformationService;
import com.vitali.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
@SessionAttributes({"userId", "orderId", "cartId", "userCart"})
public class OrderController {
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final UserInformationService userInformationService;


    @PostMapping("/order")
    public String makeOrder(HttpSession session,
                            @RequestParam String inform,
                            @ModelAttribute("userCart") Cart userCart,
                            @ModelAttribute("cartId") Integer cartId) {
        // get the user's cart
//        Cart cart = (Cart) session.getAttribute("userCart");
        List<CartItem> cartItems = userCart.getCartItems();

        // create the order

        OrderReadDto order = orderService.createOrder(cartId, inform);
        Integer orderId = order.getId();
        session.setAttribute("orderId", orderId);

        session.setAttribute("cart", userCart);
        log.info("OrderController - Post makeOrder - finish");
        return "redirect:/orderPreview/" + orderId;
    }

    @GetMapping("/orderPreview/{orderId}")
    public String orderPreview(@PathVariable("orderId") Integer orderId,
                               @ModelAttribute("userId") Integer userId,
                               @ModelAttribute("cartId") Integer cartId,
                               HttpSession session,
                               Model model) {

        // get the order
        OrderReadDto order = orderService.findById(orderId).orElse(null);
        if (order == null) {
            return "redirect:/cart/" + cartId;
        }

        // get the order items
        List<OrderItemReadDto> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        // get the user information
        UserInformationReadDto userInformation = userInformationService.findUserInformationByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        // display the order items and the user information form
        model.addAttribute("totalPrice", orderItemService.getTotalPrice(cartId));
        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("updateUserInformationUrl", "/order/updateUserInformation/" + userId);
        model.addAttribute("finishOrderUrl", "/order/finishOrder/" + orderId);

        return "orderPreview";
    }

    @PostMapping("/order/updateUserInformation/{id}")
    public String saveUserInformation(@PathVariable Integer id,
                                      @ModelAttribute("userInformation") UserInformationCreateDto userInformation,
                                      @ModelAttribute("userId") Integer userId,
                                      @ModelAttribute("orderId") Integer orderId) {
        log.info("OrderController - saveUserInformation - @PathVariable Integer id: {}", id);
        log.info("OrderController - saveUserInformation - userInformation: {}", userInformation);
        UserInformationReadDto updatedUserInformation = userInformationService.updateByUserId(userId, userInformation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("OrderController - saveUserInformation - updatedUserInformation: {}", updatedUserInformation);
        return "redirect:/orderPreview/" + orderId;
    }

    @PostMapping("/orderPreview/{orderId}")
    public String orderFinish(@PathVariable Integer orderId,
                              @ModelAttribute("userInformation") UserInformationCreateDto userInformation,
                              @ModelAttribute("userId") Integer userId,
                              @ModelAttribute("cartId") Integer cartId,
                              HttpSession session,
                              Model model) {

        // get the order
        OrderReadDto order = orderService.findById(orderId).orElse(null);
        if (order == null) {
            return "redirect:/cart/" + cartId;
        }

        // update the user information
        UserInformationReadDto updatedUserInformation = userInformationService.updateByUserId(userId, userInformation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // clear the cart
        cartItemService.deleteAllByCartId(cartId);

        // display orderItems and the updated userInformation form
        List<OrderItemReadDto> orderItems = orderItemService.findAllByOrderId(orderId);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("userInformation", updatedUserInformation);

//        return "orderPreview";
        return "redirect:/orderFinish";
    }


    @GetMapping("/finishOrder")
    public String orderFinish(HttpSession session,
                              @ModelAttribute("orderId") Integer orderId,
                              @ModelAttribute("cartId") Integer cartId,
                              Model model) {
        if (orderId == null) {
            return "redirect:/";
        }
        session.removeAttribute("orderId");

        // get the order
        OrderReadDto order = orderService.findById(orderId).orElse(null);
        if (order == null) {
            return "redirect:/cart/" + cartId;
        }

        model.addAttribute("orderId", orderId);
        return "orderFinish";
    }


}
