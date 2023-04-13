package com.vitali.services;

import com.vitali.entities.Cart;
import com.vitali.entities.OrderItem;
import com.vitali.repositories.CartRepository;
import com.vitali.repositories.OrderItemRepository;
import com.vitali.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateNewOrder() {
        // Arrange (настроить) / Given (дано)
        List<Integer> ids = Arrays.asList(1, 2, 3);
        String information = "Test information";
        Integer cartId = 1;
        Cart cart = new Cart();
        cart.setId(cartId);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2);
        OrderItem orderItem3 = new OrderItem();
        orderItem3.setId(3);
        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2, orderItem3);
        when(cartRepository.findById(anyInt())).thenReturn(java.util.Optional.of(cart));
        when(orderItemRepository.findAllByIdIn(ids)).thenReturn(orderItems);

        // Act (выполнить) / When (если)
        orderService.createNewOrder(ids, information, cartId);

        // Assert (подтвердить) / Then (то)
        assertEquals(orderItems, cart.getOrderItems());
        assertEquals(1, orderItems.get(0).getOrder().getId());
        assertEquals(1, orderItems.get(1).getOrder().getId());
        assertEquals(1, orderItems.get(2).getOrder().getId());
    }

    @Test
    public void testGetOrderItemsByIds() {
        // Arrange
        List<Integer> ids = Arrays.asList(1, 2, 3);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2);
        OrderItem orderItem3 = new OrderItem();
        orderItem3.setId(3);
        List<OrderItem> expectedOrderItems = Arrays.asList(orderItem1, orderItem2, orderItem3);
        when(orderItemRepository.findAllByIdIn(ids)).thenReturn(expectedOrderItems);

        // Act
        List<OrderItem> actualOrderItems = orderService.getOrderItemsByIds(ids);

        // Assert
        assertEquals(expectedOrderItems, actualOrderItems);
    }

    @Test
    public void testGetOrderItemsByIdsWithEmptyList() {
        // Arrange
        List<Integer> ids = Collections.emptyList();

        // Act
        List<OrderItem> actualOrderItems = orderService.getOrderItemsByIds(ids);

        // Assert
        assertEquals(Collections.emptyList(), actualOrderItems);
    }
}
