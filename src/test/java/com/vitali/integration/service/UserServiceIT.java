package com.vitali.integration.service;

import com.vitali.dto.user.UserReadDto;

import com.vitali.integration.annotation.IT;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.*;
import static com.vitali.util.TestConstants.USER_ID_ONE;
import static com.vitali.util.TestConstants.USER_READ_DTO;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceIT {
    private final UserService userService;


    @Test
    public void testFindAll() {

        // when
        List<UserReadDto> users = userService.findAll();

        // then
        assertThat(users).isNotNull();
        assertThat(users).hasSize(5);
    }

    @Test
    public void testFindById() {

        // when
        UserReadDto user = userService.findById(1);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("Jack");
    }


    @Test
    void findAll() {
    }

//    @Test
//    void findById() {
//
//        UserReadDto result = userService.findById(USER_ID_ONE);
//
//        assertThat(result).isEqualTo(USER_READ_DTO);
//    }

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