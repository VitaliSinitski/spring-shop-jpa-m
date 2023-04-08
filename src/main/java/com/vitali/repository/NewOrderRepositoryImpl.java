                    package com.vitali.repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import com.vitali.entity.Cart;
import com.vitali.entity.Order;
import com.vitali.entity.OrderItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NewOrderRepositoryImpl implements NewOrderRepository{
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
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
