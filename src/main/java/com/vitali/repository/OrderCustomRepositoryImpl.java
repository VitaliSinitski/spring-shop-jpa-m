                    package com.vitali.repository;

import java.util.ArrayList;
import java.util.List;
import com.vitali.entity.Cart;
import com.vitali.entity.Order;
import com.vitali.entity.OrderItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    // I defined this method already in OrderService
    @Override
    public void createNewOrder(List<String> orderItemsIds,  String information, Long cardId) {
        Cart cart = cartRepository.findById(cardId).orElse(null);
        List<OrderItem> orderItems = new ArrayList<>();

        for (String s : orderItemsIds) {
            orderItems.add(orderItemRepository.findById(Integer.parseInt(s)).orElse(null));
        }

        Order orders = Order.builder()
                .cart(cart)
                .build();

        orders.setOrderItems(orderItems);
    }
}
