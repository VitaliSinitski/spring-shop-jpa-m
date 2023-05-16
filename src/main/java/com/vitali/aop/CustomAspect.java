package com.vitali.aop;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.Order;
import com.vitali.database.repositories.CartItemRepository;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.product.ProductReadDto;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.exception.DeletingUserByExistingCartItemsException;
import com.vitali.exception.DeletingUserByExistingOrdersException;
import com.vitali.exception.NotEnoughProductException;
import com.vitali.exception.UserAlreadyExistsException;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CustomAspect {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Pointcut("execution(public * com.vitali.services.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Pointcut("execution(* com.vitali.controllers.CartController.*(..))")
    private void isCartControllerClass() {
    }

    @Pointcut("execution(* com.vitali.controllers.CartController.addToCart(..))")
    public void addToCartMethod() {
    }

    @Pointcut("execution(* com.vitali.controllers.UserController.delete(..))")
    public void deleteMethod() {
    }

    @Pointcut("execution(* com.vitali.controllers.UserController.create(..))")
    public void createMethodUserController() {
    }

    @Pointcut("execution(* com.vitali.services.UserService.create(..))")
    public void createMethodUserService() {
    }


    @Before(value = "addToCartMethod() " +
                    "&& args(quantity, productId, session, redirectAttributes)",
            argNames = "quantity,productId,session,redirectAttributes")
    public void beforeAddToCart(Integer quantity,
                                Integer productId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        ProductReadDto product = productService.findById(productId);
        Integer productQuantity = product.getQuantity();
        if (product == null) {
            return;
        }

        if (productQuantity < quantity) {
            throw new NotEnoughProductException("There is not enough stock for this product! Current stock quantity: "
                                                + product.getQuantity()
                                                + ", you want to order: " + quantity + ".");
        }

    }

    @Before(value = "deleteMethod() " +
                    "&& args(userId)",
            argNames = "userId")
    public void beforeUserDelete(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (!orders.isEmpty()) {
            throw new DeletingUserByExistingOrdersException("It is not possible to delete user with id: "
                                                            + userId + ", because he(she) has active orders.");
        }
        Cart cart = cartRepository.findCartByUserId(userId).orElse(null);
        if (cart != null) {
            List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
            if (!cartItems.isEmpty()) {
                throw new DeletingUserByExistingCartItemsException("It is not possible to delete user with id: "
                                                                   + userId + ", because his (her) cart is not empty.");
            }
        }
    }

    @Before(value = "createMethodUserController()" +
                    "&& args(userCreateDto, userBindingResult, userInformationCreateDto, userInformationBindingResult, redirectAttributes)",
            argNames = "userCreateDto,userBindingResult,userInformationCreateDto,userInformationBindingResult,redirectAttributes")
    public void beforeUserCreate(UserCreateDto userCreateDto,
                                 BindingResult userBindingResult,
                                 UserInformationCreateDto userInformationCreateDto,
                                 BindingResult userInformationBindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (userRepository.existsByUsername(userCreateDto.getUsername())) {
            String message = "User with username " + userCreateDto.getUsername() + " already exists";
            throw new UserAlreadyExistsException(message, userCreateDto, userInformationCreateDto);
        }
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            String message = "User with email " + userCreateDto.getEmail() + " already exists";
            throw new UserAlreadyExistsException(message, userCreateDto, userInformationCreateDto);
        }
    }

}
