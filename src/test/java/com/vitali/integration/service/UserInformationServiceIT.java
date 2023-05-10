package com.vitali.integration.service;

import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.integration.annotation.IT;
import com.vitali.services.UserInformationService;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserInformationServiceIT {
    private final UserInformationService userInformationService;


    @Test
    public void testFindAll() {
//
//        // when
//        List<UserReadDto> users = userInformationService.findAll();
//
//        // then
//        assertThat(users).isNotNull();
//        assertThat(users).hasSize(12);
    }

    @Test
    public void findUserInformationByUserId() {

        // when
        UserInformationReadDto userInformationReadDto = userInformationService.findUserInformationByUserId(1);

        // then
        assertThat(userInformationReadDto).isNotNull();
        assertThat(userInformationReadDto.getFirstName()).isEqualTo("Jack");
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