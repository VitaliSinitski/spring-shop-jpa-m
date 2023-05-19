package com.vitali.controllers;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.exception.NotEnoughStockException;
import com.vitali.exception.OutOfStockException;
import com.vitali.services.CartItemService;
import com.vitali.services.OrderItemService;
import com.vitali.services.OrderService;
import com.vitali.services.UserInformationService;
import com.vitali.validation.group.UpdateValidationGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.List;

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
                            @ModelAttribute("userId") Integer userId,
                            @ModelAttribute("cartId") Integer cartId) {
        List<CartItem> cartItems = userCart.getCartItems();

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (item.getProduct().getQuantity() < item.getQuantity()) {
                throw new NotEnoughStockException("There is not enough stock for " + product.getName()
                                                  + "! Current stock quantity: " + product.getQuantity()
                                                  + ", customer ordered: " + item.getQuantity() + ".");
            }
            if (item.getProduct().getQuantity() == null || item.getProduct().getQuantity() == 0) {
                throw new OutOfStockException("Product: " + product.getName() + " is out of stock.");
            }
        }
        OrderReadDto order = orderService.createOrder(userId, cartId, inform);
        Integer orderId = order.getId();
        session.setAttribute("orderId", orderId);
        session.setAttribute("cart", userCart);
        return "redirect:/orderPreview/" + orderId;
    }

    @GetMapping("/orderPreview/{orderId}")
    public String orderPreview(@PathVariable("orderId") Integer orderId,
                               @ModelAttribute("userId") Integer userId,
                               @ModelAttribute("cartId") Integer cartId,
                               Model model) {
        BigDecimal totalPrice = orderItemService.getTotalPrice(orderId);
        OrderReadDto order = orderService.findById(orderId);
        List<OrderItemReadDto> orderItemReadDtoList = orderItemService.findAllByOrderId(orderId);
        UserInformationReadDto userInformation = userInformationService.findUserInformationByUserId(userId);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItemReadDtoList);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("updateUserInformationUrl", "/order/updateUserInformation/" + userId);
        model.addAttribute("finishOrderUrl", "/orderPreview/" + orderId);
        return "orderPreview";
    }

    @PostMapping("/order/updateUserInformation/{id}")
    public String saveUserInformation(@PathVariable("id") Integer userInformationId,
                                      @ModelAttribute("userInformation")
                                      @Validated({Default.class, UpdateValidationGroup.class}) UserInformationCreateDto userInformation,
                                      BindingResult bindingResult,
                                      @ModelAttribute("orderId") Integer orderId,
                                      RedirectAttributes redirectAttributes,
                                      @ModelAttribute("userId") Integer userId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("userInformation", userInformation);
            return "redirect:/orderPreview/" + orderId;
        }
        userInformationService.updateUserInformation(userInformationId, userInformation);
        return "redirect:/orderPreview/" + orderId;
    }


    @PostMapping("/orderPreview/{orderId}")
    public String orderFinish(@PathVariable Integer orderId,
                              @ModelAttribute("userInformation") UserInformationCreateDto userInformation,
                              @ModelAttribute("userId") Integer userId,
                              @ModelAttribute("cartId") Integer cartId,
                              Model model) {
        cartItemService.deleteAllByCartId(cartId);
        List<OrderItemReadDto> orderItemReadDtoList = orderItemService.findAllByOrderId(orderId);
        model.addAttribute("orderItems", orderItemReadDtoList);
        model.addAttribute("userInformation", userInformation);
        return "redirect:/orderFinish/" + orderId;
    }


    @GetMapping("/orderFinish/{orderId}")
    public String orderFinish(HttpSession session,
                              @PathVariable Integer orderId,
                              @ModelAttribute("userId") Integer userId,
                              @ModelAttribute("cartId") Integer cartId,
                              Model model) {
        UserInformationReadDto userInformation = userInformationService.findUserInformationByUserId(userId);
        List<OrderItemReadDto> orderItemReadDtoList = orderItemService.findAllByOrderId(orderId);
        OrderReadDto order = orderService.findById(orderId);
        BigDecimal totalPrice = orderItemService.getTotalPrice(orderId);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("orderItems", orderItemReadDtoList);
        model.addAttribute("orderId", orderId);
        model.addAttribute("order", order);
        model.addAttribute("totalPrice", totalPrice);
        session.removeAttribute("orderId");
        return "orderFinish";
    }

}
