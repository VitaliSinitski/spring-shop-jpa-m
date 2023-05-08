package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.CartItem;
import com.vitali.database.entities.OrderItem;
import com.vitali.database.entities.Product;
import com.vitali.database.entities.User;
import com.vitali.database.entities.enums.OrderStatus;
import com.vitali.database.entities.Order;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.OrderItemRepository;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.order.OrderReadDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.mappers.cartItem.CartItemToOrderItemMapper;
import com.vitali.mappers.order.OrderReadMapper;
import com.vitali.util.ParameterUtil;
import com.vitali.util.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.vitali.database.entities.enums.OrderStatus.*;
import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderReadMapper orderReadMapper;
    @InjectMocks
    private OrderService orderService;

    @Test
    public void findAllSuccess() {
        // given
        when(orderRepository.findAll()).thenReturn(ORDER_LIST_TWO_EL);
        when(orderReadMapper.map(ORDER_ONE)).thenReturn(ORDER_READ_DTO_ONE);
        when(orderReadMapper.map(ORDER_TWO)).thenReturn(ORDER_READ_DTO_TWO);

        // when
        List<OrderReadDto> result = orderService.findAll();

        // then
        assertThat(result).hasSize(SIZE_TWO);
        assertThat(result.get(ZERO)).isEqualTo(ORDER_READ_DTO_ONE);
        assertThat(result.get(ONE)).isEqualTo(ORDER_READ_DTO_TWO);
    }

    @Test
    public void findByIdSuccess() {
        // given
        when(orderRepository.findById(ORDER_ID_ONE)).thenReturn(Optional.of(ORDER_ONE));
        when(orderReadMapper.map(ORDER_ONE)).thenReturn(ORDER_READ_DTO_ONE);

        // when
        Optional<OrderReadDto> result = orderService.findById(ORDER_ID_ONE);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ORDER_READ_DTO_ONE);
    }

    @Test
    public void updateOrderStatusSuccess() {
        // given
        Order order = new Order();
        order.setOrderStatus(PENDING);
        when(orderRepository.findById(ORDER_ID_ONE)).thenReturn(Optional.of(order));

        // when
        orderService.updateOrderStatus(CONFIRMED, ORDER_ID_ONE);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(CONFIRMED);
    }

    @Test
    public void updateOrderStatusOderIdNotFoundThrowEntityNotFoundException() {
        // given
        when(orderRepository.findById(ORDER_ID_ONE)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrderStatus(CONFIRMED, ORDER_ID_ONE));

        // then
        verify(orderRepository, times(TIMES_ONE)).findById(ORDER_ID_ONE);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder() {
    }
}