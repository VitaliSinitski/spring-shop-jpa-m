package com.vitali.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        if (request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();
//            log.info("!!! --- class UserInterceptor - preHandle - username: {}", username);
//            session.setAttribute("user", username);
            User user = userRepository.findUserByUsername(username).orElse(null);
            Cart cart = Optional.ofNullable(user.getCart()).orElse(null);
            Integer cartSize = cart.getOrderItems().size();
            session.setAttribute("currentUserName", username);
            session.setAttribute("currentUser", user);
            session.setAttribute("userCart", cart);
            session.setAttribute("cartSize", cartSize);

//            log.info("!!! --- class UserInterceptor - preHandle - currentUserName: {}", username);
//            log.info("!!! --- class UserInterceptor - preHandle - currentUser: {}", user);
//            log.info("!!! --- class UserInterceptor - preHandle - currentUser email: {}", cart);
//            log.info("!!! --- class UserInterceptor - preHandle - currentUser cartSize: {}", cartSize);

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
    }
}

