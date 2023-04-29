package com.vitali.exception;

import com.vitali.util.ParameterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public String handleOutOfStockException(OutOfStockException exception,
                                            RedirectAttributes redirectAttributes,
                                            HttpSession session) {
        Object cartIdObject = session.getAttribute("cartId");
        Integer cartId = ParameterUtil.getIntegerFromObject(cartIdObject);
//        redirectAttributes.addFlashAttribute("error", "Stock of goods is insufficient.");
//        redirectAttributes.addFlashAttribute("error", "There is not enough stock for " + exception.getMessage());
        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        return "redirect:/cart/" + cartId;
    }

}

