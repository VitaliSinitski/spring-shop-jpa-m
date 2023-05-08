package com.vitali.services;

import com.vitali.database.entities.Producer;
import com.vitali.database.repositories.ProducerRepository;
import com.vitali.dto.producer.ProducerReadDto;
import com.vitali.mappers.producer.ProducerReadMapper;
import com.vitali.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {
    @Mock
    private ProducerRepository producerRepository;
    @Mock
    private ProducerReadMapper producerReadMapper;
    @InjectMocks
    private ProducerService producerService;

    @Test
    public void findAllSuccess() {
        // given
        List<Producer> producers = Collections.singletonList(PRODUCER);
        when(producerRepository.findAll()).thenReturn(producers);
        when(producerReadMapper.map(PRODUCER)).thenReturn(PRODUCER_READ_DTO);

        // when
        List<ProducerReadDto> result = producerService.findAll();

        // then
        assertThat(result).hasSize(SIZE_ONE);
        assertThat(result.get(SIZE_ZERO).getName()).isEqualTo("Test Product");
    }

}