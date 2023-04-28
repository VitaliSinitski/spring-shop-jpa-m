package com.vitali.mappers.order;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.repositories.UserRepository;
import com.vitali.mappers.Mapper;
import com.vitali.database.repositories.CartRepository;
import com.vitali.dto.order.OrderCreateDto;
import com.vitali.database.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.vitali.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class OrderCreateMapper implements Mapper<OrderCreateDto, Order> {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public Order map(OrderCreateDto object) {
        return Order.builder()
//                .cart(cartRepository.findById(object.getCartId())
//                        .orElseThrow(IllegalArgumentException::new))
                .cart(getCart(object.getCartId()))
                .user(getUser(object.getUserId()))
//                .orderStatus(Optional.ofNullable(object.getOrderStatus()).orElse(DEFAULT_ORDER_STATUS))
                .inform(object.getInform())
                .build();
    }

    public Cart getCart(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(cartRepository::findById)
                .orElse(null);
    }

    public User getUser(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
