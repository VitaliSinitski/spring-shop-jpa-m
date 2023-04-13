package com.vitali.mappers.producer;

import com.vitali.dto.producer.ProducerReadDto;
import com.vitali.entities.Producer;
import com.vitali.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProducerReadDto> mapList(List<Producer> objects) {
        if (objects == null || objects.isEmpty()) {
            return Collections.emptyList();
        }
        return objects
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
