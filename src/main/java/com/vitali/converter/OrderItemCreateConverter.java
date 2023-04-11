package com.vitali.converter;

import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.util.ParameterUtil;

import javax.servlet.http.HttpServletRequest;

import static com.vitali.constants.Constants.CART_ID;
import static com.vitali.constants.Constants.PRODUCT_ID;

public class OrderItemCreateConverter implements Converter<HttpServletRequest, OrderItemCreateDto> {
    @Override
    public OrderItemCreateDto convert(HttpServletRequest request) {
        return OrderItemCreateDto.builder()
                .quantity(ParameterUtil.getQuantity(request))
                .productId(ParameterUtil.getIntegerFromRequest(PRODUCT_ID, request))
                .cartId(ParameterUtil.getLongFromRequest(CART_ID, request))
                .build();
    }

}
