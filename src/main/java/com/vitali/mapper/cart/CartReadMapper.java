package com.vitali.mapper.cart;

import com.vitali.dto.CartReadDto;
import com.vitali.entity.Cart;
import com.vitali.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements Mapper<Cart, CartReadDto> {
    @Override
    public CartReadDto mapFrom(Cart object) {
        return CartReadDto.builder()
                .id(object.getId())
                .createTime(object.getCreateTime())
                .build();
    }

    @Override
    public CartReadDto map(Cart object) {
        return null;
    }

    @Override
    public List<CartReadDto> mapList(List<Cart> objects) {
        return null;
    }
}
