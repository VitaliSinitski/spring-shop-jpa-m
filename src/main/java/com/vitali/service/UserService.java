package com.vitali.service;

import com.vitali.dto.user.UserReadDto;
import com.vitali.entity.User;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.mapper.producer.ProducerCreateMapper;
import com.vitali.mapper.producer.ProducerReadMapper;
import com.vitali.mapper.user.UserCreateMapper;
import com.vitali.mapper.user.UserReadMapper;
import com.vitali.repository.ProducerRepository;
import com.vitali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public Long create(UserCreateDto userCreateDto) {
        User userEntity = userCreateMapper.map(userCreateDto);
        return userRepository.save(userEntity).getId();
    }

    public Optional<UserReadDto> login(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password)
                .map(userReadMapper::map);
    }
}
