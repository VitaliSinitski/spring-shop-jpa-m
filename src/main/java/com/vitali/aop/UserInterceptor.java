package com.vitali.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
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
            log.info("!!! --- class UserInterceptor - preHandle - username: {}", username);
            Optional<User> userOptional = userRepository.findUserByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Integer userId = user.getId();
                session.setAttribute("currentUserName", username);
                log.info("!!! --- class UserInterceptor - preHandle - currentUserName: {}", username);
                session.setAttribute("currentUser", user);
                log.info("!!! --- class UserInterceptor - preHandle - currentUser: {}", user);
                session.setAttribute("userId", userId);
                log.info("!!! --- class UserInterceptor - preHandle - userId: {}", userId);
                Cart cart = user.getCart();
                if (cart != null) {
                    Integer cartSize = Optional.ofNullable(cart.getCartItems()).map(List::size).orElse(0);
                    if (cartSize != 0) {
                        Integer cartId = cart.getId();
                        session.setAttribute("userCart", cart);
                        log.info("!!! --- class UserInterceptor - preHandle - currentUser email: {}", cart);
                        session.setAttribute("cartSize", cartSize);
                        log.info("!!! --- class UserInterceptor - preHandle - currentUser cartSize: {}", cartSize);
                        session.setAttribute("cartId", cartId);
                        log.info("!!! --- class UserInterceptor - preHandle - cartId: {}", cartId);
                        UserInformation userInformation = user.getUserInformation();
                        Integer userInformationId = Optional.ofNullable(userInformation).map(UserInformation::getId).orElse(0);
                        if (userInformationId != 0) {
                            session.setAttribute("userInformation", userInformation);
                            session.setAttribute("userInformationId", userInformationId);
                        }
                    }
                }
            }
        }
        return true;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        HttpSession session = request.getSession();
//        if (request.getUserPrincipal() != null) {
//            String username = request.getUserPrincipal().getName();
////            log.info("!!! --- class UserInterceptor - preHandle - username: {}", username);
////            session.setAttribute("user", username);
//            User user = userRepository.findUserByUsername(username).orElse(null);
//            Cart cart = Optional.ofNullable(user.getCart()).orElse(null);
////            Integer cartSize = cart.getOrderItems().size();
//            Integer cartSize = cart.getCartItems().size();
//            Integer cartId = cart.getId();
//            Integer userId = user.getId();
//            UserInformation userInformation = user.getUserInformation();
//            Integer userInformationId = userInformation.getId();
//            session.setAttribute("currentUserName", username);
//            session.setAttribute("currentUser", user);
//            session.setAttribute("userCart", cart);
//            session.setAttribute("cartSize", cartSize);
//            session.setAttribute("cartId", cartId);
//            session.setAttribute("userId", userId);
//            session.setAttribute("userInformation", userInformation);
//            session.setAttribute("userInformationId", userInformationId);
//
////            log.info("!!! --- class UserInterceptor - preHandle - currentUserName: {}", username);
////            log.info("!!! --- class UserInterceptor - preHandle - cartId: {}", cartId);
////            log.info("!!! --- class UserInterceptor - preHandle - currentUser: {}", user);
////            log.info("!!! --- class UserInterceptor - preHandle - currentUser email: {}", cart);
////            log.info("!!! --- class UserInterceptor - preHandle - currentUser cartSize: {}", cartSize);
//
//        }
//        return true;
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
    }
}

