package com.vitali.dto.orderItem;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class OrderItemCreateDto {
//    LocalDateTime createTime;
    private Integer quantity;
    private Integer productId;
    private Integer cartId;
//    BigDecimal productPrice;
//    Long categoryId;
//    Long producerId;

}
