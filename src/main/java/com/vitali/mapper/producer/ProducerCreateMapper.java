package com.vitali.mapper.producer;

import com.vitali.shop.dto.ProducerCreateDto;
import com.vitali.shop.entity.Producer;

import java.util.List;

public class ProducerCreateMapper implements Mapper<ProducerCreateDto, Producer> {
    @Override
    public Producer mapFrom(ProducerCreateDto object) {
        return Producer.builder()
                .name(object.getName())
                .build();
    }

    @Override
    public List<Producer> mapListFrom(List<ProducerCreateDto> objects) {
        return null;
    }
}
