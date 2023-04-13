package com.vitali.mappers.producer;

import com.vitali.dto.producer.ProducerCreateDto;
import com.vitali.entities.Producer;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerCreateMapper implements Mapper<ProducerCreateDto, Producer> {
    @Override
    public Producer map(ProducerCreateDto object) {
        return Producer.builder()
                .name(object.getName())
                .build();
    }
}
