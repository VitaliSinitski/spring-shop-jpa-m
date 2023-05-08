package com.vitali.services;

import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCreateMapper userCreateMapper;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void findAll() {
    }

    @Test
    void findById() {
//        Mockito.doReturn(new User(USER_ID, USER_NAME, USER_PASSWORD))
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}