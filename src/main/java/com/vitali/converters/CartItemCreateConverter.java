package com.vitali.converters;

import com.vitali.dto.cartItem.CartItemCreateDto;
import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.util.ParameterUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.vitali.constants.Constants.PRODUCT_ID;

@Component
public class CartItemCreateConverter implements Converter<HttpServletRequest, CartItemCreateDto> {
    @Override
    public CartItemCreateDto convert(HttpServletRequest request) {
        return CartItemCreateDto.builder()
                .quantity(ParameterUtil.getQuantity(request))
                .productId(ParameterUtil.getIntegerFromRequest(PRODUCT_ID, request))
//                .cartId(ParameterUtil.getIntegerFromRequest(CART_ID, request))
                .build();
    }

}
