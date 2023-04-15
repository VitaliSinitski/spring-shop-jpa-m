package com.vitali.dto.orderItem;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderItemCreateDto {
//    LocalDateTime createTime;
    private Integer quantity;
    private Integer productId;
    private Integer cartId;
//    BigDecimal productPrice;
//    Long categoryId;
//    Long producerId;

}
