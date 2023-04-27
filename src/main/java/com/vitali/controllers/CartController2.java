package com.vitali.controllers;

import com.vitali.converters.CartItemCreateConverter;
import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.cartItem.CartItemReadDto;
import com.vitali.services.CartItemService;
import com.vitali.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

//@Slf4j
//@Controller
@RequiredArgsConstructor
//@RequestMapping("/cart")
public class CartController2 {
    private final CartItemService cartItemService;
    private final CartItemCreateConverter cartItemCreateConverter;

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

//    @GetMapping("/delete")
    public String deleteCartItemGet(@RequestParam Integer itemId,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cartId = (Integer) session.getAttribute("cartId");
//        log.info("CartController - cartId: {}", cartId);
        cartItemService.delete(itemId);
        return "redirect:/cart/" + cartId;
    }


//    @PostMapping(params = "delete")
//    public String deleteCartItem(@RequestParam Integer delete,
//                                 RedirectAttributes redirectAttributes) {
//        cartItemService.delete(delete);
//        redirectAttributes.addAttribute("cartId", session.getAttribute("cartId"));
//        return "redirect:/cart/{cartId}";
//    }


//    @PostMapping("/deleteAll")
    public String deleteAllCartItems(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cartId = ParameterUtil.getIntegerFromObject(session.getAttribute("cartId"));
        cartItemService.deleteAllByCartId(cartId);
        return "redirect:/products";
    }


//    @PostMapping("/order")
//    public String createProduct(@ModelAttribute ProductCreateDto product,
//                                HttpServletRequest request,
//                                Model model) {
//        int cartId = Integer.parseInt(request.getParameter("cartId"));
//        log.info("OrderController - cartId: {}", cartId);
//        return "admin/products";
//    }

//    @PostMapping("/order")
//    public String createOrder(@RequestParam("selectedItems") List<Integer> selectedItems,
//                              @RequestParam Integer cartId,
////                              @RequestParam String inform,
//                              @RequestParam OrderStatus orderStatus,
////                              HttpServletRequest request,
//                              Model model) {
//        OrderReadDto order = cartService.createOrder(cartId, /*inform, */orderStatus, selectedItems);
//        List<OrderItemReadDto> orderItems = orderItemService.findAllByOrderId(order.getId());
////        HttpSession session = request.getSession();
////        User currentUser = (User) session.getAttribute("currentUser");
////        UserInformation userInformation = currentUser.getUserInformation();
//        model.addAttribute("order", order);
//        model.addAttribute("orderItems", orderItems);
//        return "redirect:/orderConfirmation";
//    }
//
//    @PostMapping("/order/confirm")
//    public String orderConfirmation(@RequestParam("selectedItems") List<Integer> selectedItems,
//                              @RequestParam Integer cartId,
////                              @RequestParam String inform,
//                              @RequestParam OrderStatus orderStatus) {
//
//        cartService.createOrder(cartId, /*inform, */orderStatus, selectedItems);
//        return "redirect:/orderConfirmation";
//    }



//    public void getUserInformation(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        User currentUser = (User) session.getAttribute("currentUser");
//        Cart userCart = (Cart) session.getAttribute("userCart");
//        Integer cartId = (Integer) session.getAttribute("cartId");
//        Integer userId = currentUser.getId();
//        UserInformation userInformation = currentUser.getUserInformation();
//    }


}
