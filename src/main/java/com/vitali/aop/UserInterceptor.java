package com.vitali.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.entities.enums.Role;
import com.vitali.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        if (request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();
            Optional<User> userOptional = userRepository.findUserByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!user.getRole().equals(Role.ADMIN)) {
                    if (session.getAttribute("currentUserName") == null) {
                        session.setAttribute("currentUserName", username);
                        log.info("!!! --- class UserInterceptor - preHandle - currentUserName: {}", username);
                    }
                    if (session.getAttribute("currentUser") == null || session.getAttribute("userId") == null) {
                        Integer userId = user.getId();
                        session.setAttribute("currentUser", user);
                        log.info("!!! --- class UserInterceptor - preHandle - currentUser: {}", user);
                        session.setAttribute("userId", userId);
                        log.info("!!! --- class UserInterceptor - preHandle - userId: {}", userId);
                    }
                    if (session.getAttribute("userInformation") == null || session.getAttribute("userInformationId") == null) {
                        UserInformation userInformation = user.getUserInformation();
                        Integer userInformationId = Optional.ofNullable(userInformation).map(UserInformation::getId).orElse(0);
                        if (userInformationId != 0) {
                            session.setAttribute("userInformation", userInformation);
                            session.setAttribute("userInformationId", userInformationId);
                        }
                    }
                    Cart cart = user.getCart();
                    if (cart != null) {
                        if (session.getAttribute("userCart") == null || session.getAttribute("cartId") == null) {
                            Integer cartId = cart.getId();
                            session.setAttribute("userCart", cart);
                            log.info("!!! --- class UserInterceptor - preHandle - cart: {}", cart);
                            session.setAttribute("cartId", cartId);
                            log.info("!!! --- class UserInterceptor - preHandle - cartId: {}", cartId);
                        }
                        Integer cartSize = Optional.ofNullable(cart.getCartItems()).map(List::size).orElse(0);
                        if (!cartSize.equals(session.getAttribute("cartSize"))) {
//                        if (cartSize != 0) {
                            session.setAttribute("cartSize", cartSize);
                            log.info("!!! --- class UserInterceptor - preHandle - currentUser cartSize: {}", cartSize);
                        }
                    }
                }
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
    }
}

