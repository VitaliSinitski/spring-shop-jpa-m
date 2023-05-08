package com.vitali.services;

import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import com.vitali.database.repositories.OrderItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemReadMapper orderItemReadMapper;
    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void findAllByOrderIdSuccess() {
        // given
        when(orderItemRepository.findOrderItemsByOrderId(ORDER_ID_ONE)).thenReturn(ORDER_ITEM_LIST_TWO_EL);
        when(orderItemReadMapper.map(ORDER_ITEM_ONE)).thenReturn(ORDER_ITEM_READ_DTO_ONE);
        when(orderItemReadMapper.map(ORDER_ITEM_TWO)).thenReturn(ORDER_ITEM_READ_DTO_TWO);

        // when
        List<OrderItemReadDto> result = orderItemService.findAllByOrderId(ORDER_ID_ONE);

        // then
        assertThat(result).containsExactlyInAnyOrder(ORDER_ITEM_READ_DTO_ONE, ORDER_ITEM_READ_DTO_TWO);
    }


    @Test
    public void getTotalPriceSuccess() {
        // given
        when(orderItemRepository.findOrderItemsByOrderId(anyInt())).thenReturn(ORDER_ITEM_LIST_ONE_EL);

        // when
        BigDecimal totalPrice = orderItemService.getTotalPrice(ORDER_ID_ONE);

        // then
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(QUANTITY_TWO).multiply(PRODUCT.getPrice());
        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice);
    }

    @Test
    public void getTotalPriceOrderItemNotFound() {
        // given
        when(orderItemRepository.findOrderItemsByOrderId(ORDER_ID_ONE)).thenReturn(ORDER_ITEM_LIST_ZERO_EL);

        // when
        BigDecimal totalPrice = orderItemService.getTotalPrice(ORDER_ID_ONE);

        // then
        Assertions.assertThat(totalPrice).isEqualTo(BigDecimal.ZERO);
    }
}