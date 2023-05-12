package com.vitali.handler;

import com.vitali.database.repositories.FilterProductRepository;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.exception.*;
import com.vitali.util.ParameterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Slf4j
@ControllerAdvice/*(basePackages = "com.vitali")*/
public class ExceptionHandlerController /*extends ResponseEntityExceptionHandler*/ {
    private final FilterProductRepository productRepository;

    public ExceptionHandlerController(@Qualifier("productRepository") FilterProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ExceptionHandler(OutOfStockException.class)
    public String handleOutOfStockException(OutOfStockException exception,
                                            RedirectAttributes redirectAttributes,
                                            HttpSession session) {
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
        redirectAttributes.addFlashAttribute("message", exception.getMessage());
        return "redirect:/cart/" + cartId;
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public String handleNotEnoughStockException(NotEnoughStockException exception,
                                                RedirectAttributes redirectAttributes,
                                                HttpSession session) {
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
        redirectAttributes.addFlashAttribute("message", exception.getMessage());
        return "redirect:/cart/" + cartId;
    }

    @ExceptionHandler(NotEnoughProductException.class)
    public String handleNotEnoughProductException(NotEnoughProductException exception,
                                                  RedirectAttributes redirectAttributes,
                                                  HttpSession session) {
        Integer productId = ParameterUtil.getIntegerFromObject(session.getAttribute("productId"));
        redirectAttributes.addFlashAttribute("message", exception.getMessage());
        if (productId != null) {
            return "redirect:/products/" + productId;
        }
        return "redirect:/products";
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("message", "User not found");
//        body.put("timestamp", LocalDateTime.now());
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public String handleEntityNotFoundException(EntityNotFoundException exception,
//                                                RedirectAttributes redirectAttributes,
//                                                WebRequest request) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("message", "User not found");
//        body.put("timestamp", LocalDateTime.now());
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException exception,
                                                Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error/error";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUsernameNotFoundException(UsernameNotFoundException exception,
                                                  Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error/error";
    }

    @ExceptionHandler(UpdatingPasswordNotMatchingException.class)
    public String handleUpdatingPasswordNotMatchingException(UpdatingPasswordNotMatchingException exception,
                                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        return "user/user-edit-password";
    }

    @ExceptionHandler(RegistrationPasswordNotMatchingException.class)
    public String handleRegistrationPasswordNotMatchingException(RegistrationPasswordNotMatchingException exception,
                                                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        return "redirect:/registration";
    }

    @ExceptionHandler(DeletingUserByExistingOrdersException.class)
    public String handleDeletingUserByExistingOrdersException(DeletingUserByExistingOrdersException exception,
                                                              RedirectAttributes redirectAttributes,
                                                              HttpSession session) {
        Object userIdObject = session.getAttribute("userId");
        Integer userId = ParameterUtil.getIntegerFromObject(userIdObject);
        if (userId != null) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/users/" + userId;
        }
        return "redirect:/users";
    }

    @ExceptionHandler(DeletingUserByExistingCartItemsException.class)
    public String handleDeletingUserByExistingCartItemsException(DeletingUserByExistingCartItemsException exception,
                                                              RedirectAttributes redirectAttributes,
                                                              HttpSession session) {
        Object userIdObject = session.getAttribute("userId");
        Integer userId = ParameterUtil.getIntegerFromObject(userIdObject);
        if (userId != null) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/users/" + userId;
        }
        return "redirect:/users";
    }

}
