package com.vitali.integration.service;

import com.vitali.database.entities.Order;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.repositories.OrderRepository;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.integration.annotation.IT;
import com.vitali.services.UserInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import static com.vitali.util.MockUtils.USER_INFORMATION_CREATE_DTO;
import static com.vitali.util.TestConstants.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserInformationServiceIT {
    private final UserInformationService userInformationService;
    private final UserInformationRepository userInformationRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Test
    public void findUserInformationByUserId() {
        // given
        User expectedUser = userRepository.findAll().get(ZERO);
        Integer userId = expectedUser.getId();
        UserInformation expectedUserInformation = userInformationRepository.findUserInformationByUserId(userId).get();

        // when
        UserInformationReadDto actualUserInformation = userInformationService.findUserInformationByUserId(userId);

        // then
        assertThat(actualUserInformation).isNotNull();
        assertThat(actualUserInformation.getFirstName()).isEqualTo(expectedUserInformation.getFirstName());
        assertThat(actualUserInformation.getLastName()).isEqualTo(expectedUserInformation.getLastName());
        assertThat(actualUserInformation.getPhone()).isEqualTo(expectedUserInformation.getPhone());
        assertThat(actualUserInformation.getAddress()).isEqualTo(expectedUserInformation.getAddress());
        assertThat(actualUserInformation.getBirthDate()).isEqualTo(expectedUserInformation.getBirthDate());

    }
    @Test
    public void findUserInformationByOrderId() {
        // given
        Order expectedOrder = orderRepository.findAll().get(ZERO);
        Integer expectedOrderId = expectedOrder.getId();
        UserInformation expectedUserInformation = expectedOrder.getUser().getUserInformation();

        // when
        UserInformationReadDto actualUserInformation = userInformationService.findUserInformationByOrderId(expectedOrderId);

        // then
        assertThat(actualUserInformation.getId()).isEqualTo(expectedUserInformation.getId());
        assertThat(actualUserInformation.getFirstName()).isEqualTo(expectedUserInformation.getFirstName());
        assertThat(actualUserInformation.getLastName()).isEqualTo(expectedUserInformation.getLastName());
        assertThat(actualUserInformation.getPhone()).isEqualTo(expectedUserInformation.getPhone());
        assertThat(actualUserInformation.getAddress()).isEqualTo(expectedUserInformation.getAddress());
        assertThat(actualUserInformation.getBirthDate()).isEqualTo(expectedUserInformation.getBirthDate());
    }


    @Test
    public void updateUserInformation() {
        // given
        UserInformation userInformation = userInformationRepository.findAll().get(ZERO);
        Integer userInformationId = userInformation.getId();

        // when
        userInformationService.updateUserInformation(userInformationId, USER_INFORMATION_CREATE_DTO);

        // then
        UserInformation updatedUserInformation = userInformationRepository.findById(userInformationId).orElse(null);
        assertThat(updatedUserInformation).isNotNull();
        assertThat(updatedUserInformation.getFirstName()).isEqualTo(USER_INFORMATION_CREATE_DTO.getFirstName());
        assertThat(updatedUserInformation.getLastName()).isEqualTo(USER_INFORMATION_CREATE_DTO.getLastName());
        assertThat(updatedUserInformation.getPhone()).isEqualTo(USER_INFORMATION_CREATE_DTO.getPhone());
        assertThat(updatedUserInformation.getAddress()).isEqualTo(USER_INFORMATION_CREATE_DTO.getAddress());
        assertThat(updatedUserInformation.getBirthDate()).isEqualTo(USER_INFORMATION_CREATE_DTO.getBirthDate());

    }
}