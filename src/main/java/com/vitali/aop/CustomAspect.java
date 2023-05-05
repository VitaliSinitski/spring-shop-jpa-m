package com.vitali.aop;

import com.vitali.dto.product.ProductReadDto;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CustomAspect {

    private final ProductService productService;

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {}

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {}

    @Pointcut("execution(public * com.vitali.services.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {}

    @Pointcut("execution(* com.vitali.controllers.CartController.*(..))")
    private void isCartControllerClass() {}

    @Pointcut("execution(* com.vitali.controllers.CartController.addToCart(..))")
    public void addToCartMethod() {}


    @Before(value = "addToCartMethod() " +
                    "&& args(quantity, productId, session, redirectAttributes)",
            argNames = "quantity,productId,session,redirectAttributes")
    public void logAddToCart(Integer quantity,
                             Integer productId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        ProductReadDto product = productService.findById(productId);
        Integer productQuantity = product.getQuantity();
        if (product == null) {
            return;
        }
        log.info("Adding {} units of product {} to cart", quantity, product.getName());

        Integer remainingQuantity = productQuantity - quantity;
        log.info("Product {} has {} units remaining in stock", product.getName(), remainingQuantity);
    }



//    @Before(value = "isCartControllerClass() && addToCartMethod() && args(quantity, productId,..)",
//            argNames = "quantity,productId")
//    public void logAddToCart(Integer quantity, Integer productId) {
//        log.info("Adding {} quantity of product with id {} to cart", quantity, productId);
//    }



    //    @Before("execution(public * com.vitali.services.*Service.findById(*))") // may put pointcut description inside
//    @Before(value = "anyFindByIdServiceMethod() " +          // or can refer ot that pointcut
//                    "&& args(id) " +
//                    "&& target(service) " +
//                    "&& this(serviceProxy)" +
//                    "&& @within(transactional)",
//            argNames = "joinPoint,id,service,serviceProxy,transactional")
//    public void addLogging(JoinPoint joinPoint,         // JoinPoint must be first parameter
//                           Object id,
//                           Object service,
//                           Object serviceProxy,
//                           Transactional transactional) {
//        log.info("before - invoked findById method in class {}, with id {}", service, id);
//    }
}
