package com.vitali.services;


import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.dto.orderItem.OrderItemReadDto;
import com.vitali.entities.OrderItem;
import com.vitali.mappers.orderItem.OrderItemCreateMapper;
import com.vitali.mappers.orderItem.OrderItemReadMapper;
import com.vitali.repositories.OrderItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemReadMapper orderItemReadMapper;

    @Mock
    private OrderItemCreateMapper orderItemCreateMapper;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void testCreate() {
        // given
        OrderItemCreateDto orderItemCreateDto = new OrderItemCreateDto();
        OrderItem orderItemEntity = new OrderItem();
        when(orderItemCreateMapper.map(orderItemCreateDto)).thenReturn(orderItemEntity);
        when(orderItemRepository.save(orderItemEntity)).thenReturn(orderItemEntity);

        // when
        Integer result = orderItemService.create(orderItemCreateDto);

        // then
        verify(orderItemCreateMapper, times(1)).map(orderItemCreateDto);
        verify(orderItemRepository, times(1)).save(orderItemEntity);
        assertEquals(orderItemEntity.getId(), result);
    }

    @Test
    public void testFindById() {
        // given
        Integer orderId = 1;
        OrderItem orderItem = Mockito.mock(OrderItem.class);
        OrderItemReadDto orderItemReadDto = Mockito.mock(OrderItemReadDto.class);
        Mockito.when(orderItemRepository.findById(orderId)).thenReturn(Optional.of(orderItem));
        Mockito.when(orderItemReadMapper.map(orderItem)).thenReturn(orderItemReadDto);

        // when
        Optional<OrderItemReadDto> foundOrderItem = orderItemService.findById(orderId);

        // then
        Assertions.assertTrue(foundOrderItem.isPresent());
        Assertions.assertEquals(orderItemReadDto, foundOrderItem.get());
    }

    @Test
    public void testFindAllByCartId() {
        // given
        Long cartId = 1L;
        List<OrderItem> orderItemList = List.of(new OrderItem());
        List<OrderItemReadDto> orderItemReadDtoList = List.of(new OrderItemReadDto());
        when(orderItemRepository.findOrderItemsByCartId(cartId)).thenReturn(orderItemList);
        when(orderItemReadMapper.map(any(OrderItem.class))).thenReturn(new OrderItemReadDto());

        // when
        List<OrderItemReadDto> result = orderItemService.findAllByCartId(cartId);

        // then
        verify(orderItemRepository, times(1)).findOrderItemsByCartId(cartId);
        verify(orderItemReadMapper, times(orderItemList.size())).map(any(OrderItem.class));
        assertEquals(orderItemList.size(), result.size());
    }

    @Test
    public void testFindOrderItemByProductId() {
        // given
        Integer productId = 1;
        when(orderItemRepository.existsOrderItemByProductId(productId)).thenReturn(true);

        // when
        boolean result = orderItemService.findOrderItemByProductId(productId);

        // then
        verify(orderItemRepository, times(1)).existsOrderItemByProductId(productId);
        assertEquals(true, result);
    }

    @Test
    public void testDelete() {
        // given
        Integer id = 1;
        OrderItem orderItemEntity = new OrderItem();
        Optional<OrderItem> optionalOrderItem = Optional.of(orderItemEntity);
        when(orderItemRepository.findById(id)).thenReturn(optionalOrderItem);

        // when
        boolean result = orderItemService.delete(id);

        // then
        verify(orderItemRepository, times(1)).findById(id);
        verify(orderItemRepository, times(1)).delete(orderItemEntity);
        assertEquals(true, result);
    }
}