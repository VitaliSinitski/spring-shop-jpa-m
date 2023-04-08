package com.vitali.mapper.producer;

import com.vitali.dto.ProducerReadDto;
import com.vitali.entity.Producer;
import com.vitali.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProducerReadMapper implements Mapper<Producer, ProducerReadDto> {
    @Override
    public ProducerReadDto map(Producer object) {
        return ProducerReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public List<ProducerReadDto> mapList(List<Producer> objects) {
        return null;
    }
}
