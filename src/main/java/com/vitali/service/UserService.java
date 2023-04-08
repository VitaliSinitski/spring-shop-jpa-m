package com.vitali.service;

import com.vitali.mapper.producer.ProducerCreateMapper;
import com.vitali.mapper.producer.ProducerReadMapper;
import com.vitali.mapper.user.UserCreateMapper;
import com.vitali.mapper.user.UserReadMapper;
import com.vitali.repository.ProducerRepository;
import com.vitali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;
}
