package com.vitali.security;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.user.UserReadDto;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
//    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("method onAuthenticationSuccess started");
        HttpSession session = request.getSession();
        String username = authentication.getName();
//        UserReadDto user = userService.findUserByUsername();
        User user = userRepository.findUserByUsername(username).orElse(null);
        Cart cart = Optional.ofNullable(user.getCart()).orElse(null);
        int cartSize = cart.getOrderItems().size();
        session.setAttribute("currentUserName", username);
        session.setAttribute("currentUser", user);
        session.setAttribute("userCart", cart);
        session.setAttribute("cartSize", cartSize);

        log.info("currentUserName: {}", username);
        log.info("currentUser: {}", user);
        log.info("currentUser email: {}", cart);
        log.info("currentUser cartSize: {}", cartSize);
        response.sendRedirect("/");
    }
}