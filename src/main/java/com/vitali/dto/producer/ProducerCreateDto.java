package com.vitali.dto.producer;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProducerCreateDto {
    String name;
}
