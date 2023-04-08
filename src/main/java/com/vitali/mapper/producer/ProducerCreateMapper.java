package com.vitali.mapper.producer;

import com.vitali.dto.producer.ProducerCreateDto;
import com.vitali.entity.Producer;
import com.vitali.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
