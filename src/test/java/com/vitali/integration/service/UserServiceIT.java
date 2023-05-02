package com.vitali.integration.service;

import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import com.vitali.services.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceIT {

    @Autowired
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