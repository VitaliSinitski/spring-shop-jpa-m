package com.vitali.converters;

import com.vitali.dto.orderItem.OrderItemCreateDto;
import com.vitali.util.ParameterUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.vitali.constants.Constants.PRODUCT_ID;

@Component
public class OrderItemCreateConverter implements Converter<HttpServletRequest, OrderItemCreateDto> {
    @Override
    public OrderItemCreateDto convert(HttpServletRequest request) {
        return OrderItemCreateDto.builder()
                .quantity(ParameterUtil.getQuantity(request))
                .productId(ParameterUtil.getIntegerFromRequest(PRODUCT_ID, request))
//                .cartId(ParameterUtil.getLongFromRequest(CART_ID, request))
                .cartId(1) // TODO: 12.04.2023 put cartId
                .build();
    }

}
