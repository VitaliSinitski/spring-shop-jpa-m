package com.vitali.exception;

import com.vitali.util.ParameterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Slf4j
@ControllerAdvice(basePackages = "com.vitali")
public class CustomExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public String handleOutOfStockException(OutOfStockException exception,
                                            RedirectAttributes redirectAttributes,
                                            HttpSession session) {
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
//        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        redirectAttributes.addFlashAttribute("message", exception.getMessage());
        return "redirect:/cart/" + cartId;
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


}

