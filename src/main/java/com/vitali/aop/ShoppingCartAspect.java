package com.vitali.aop;

import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Product;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.exception.NotEnoughStockException;
import com.vitali.exception.OutOfStockException;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ShoppingCartAspect {
    private final ProductService productService;

//    @Pointcut("execution(* com.vitali.controllers.*Controller.*(..))")
//    public void controllerMethod() {}

    @Pointcut(value = "execution(* com.vitali.controllers.CartController.addToCart(..)) && args(productId, quantity)",
            argNames = "productId,quantity")
    public void beforeAddProductToCart(Integer productId, Integer quantity) {}

    @Around(value = "beforeAddProductToCart(productId, quantity)", argNames = "joinPoint,productId,quantity")
    public Object checkProductStock(ProceedingJoinPoint joinPoint, Integer productId, Integer quantity) throws Throwable {
        log.info("ShoppingCartAspect - checkProductStock - start");
        log.info("ShoppingCartAspect - checkProductStock - productId, quantity {}, {}", productId, quantity);
        ProductReadDto product = productService.findById(productId);
        if (product == null) {
            log.info("ShoppingCartAspect - checkProductStock - throw new OutOfStockException");
            throw new OutOfStockException("Product: " + product.getName() + " is out of stock!!!");
        }
        if (product.getQuantity() < quantity) {
            log.info("ShoppingCartAspect - checkProductStock - throw new NotEnoughStockException");
            throw new NotEnoughStockException("There is not enough stock for " + product.getName()
                                              + "! Current stock quantity: " + product.getQuantity()
                                              + ", customer ordered: " + quantity + "!!!");
        }

        return joinPoint.proceed();
    }








//    @Before(value = "beforeAddProductToCart(productId, quantity)", argNames = "productId,quantity")
//    public void checkProductStock(Integer productId, Integer quantity) {
//        log.info("ShoppingCartAspect - checkProductStock - start");
//        log.info("ShoppingCartAspect - checkProductStock - productId, quantity {}, {}", productId, quantity);
//        ProductReadDto product = productService.findById(productId);
//        if (product == null) {
//            log.info("ShoppingCartAspect - checkProductStock - throw new OutOfStockException");
//            throw new OutOfStockException("Product: " + product.getName() + " is out of stock!!!");
//        }
//        if (product.getQuantity() < quantity) {
//            log.info("ShoppingCartAspect - checkProductStock - throw new NotEnoughStockException");
//            throw new NotEnoughStockException("There is not enough stock for " + product.getName()
//                                              + "! Current stock quantity: " + product.getQuantity()
//                                              + ", customer ordered: " + quantity + "!!!");
//        }
//    }
}

//    @Before(value = "controllerMethod() && args(productId, quantity)", argNames = "productId,quantity")
//    public void beforeAddProductToCart(Integer productId, Integer quantity) {
//        ProductReadDto product = productService.findById(productId);
//        if (product == null) {
//            throw new OutOfStockException("Product: " + product.getName()
//                                          + " is out of stock.");
//        }
//        if (product.getQuantity() < quantity) {
//            throw new NotEnoughStockException("There is not enough stock for " + product.getName()
//                                              + "! Current stock quantity: " + product.getQuantity()
//                                              + ", customer ordered: " + quantity + ".");
//        }
//    }

//    @AfterReturning(pointcut = "controllerMethod() && args(productId, quantity)", returning = "cartItem", argNames = "productId,quantity,cartItem")
//    public void afterAddProductToCart(Integer productId, Integer quantity, CartItem cartItem) {
//        cartService.updateCart(cartItem.getCartId());
//    }
//
//    @AfterReturning("execution(* com.vitali.services.CartItemService.getTotalPrice(..))")
//    public void afterUpdateCart(JoinPoint joinPoint) {
//        cartItemService.getTotalPrice();
//    }
//}

