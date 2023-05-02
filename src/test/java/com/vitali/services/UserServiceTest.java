package com.vitali.services;

import com.vitali.database.entities.User;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import com.vitali.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.vitali.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;


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