package com.vitali.services;

import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.mappers.userInformation.UserInformationCreateMapper;
import com.vitali.mappers.userInformation.UserInformationReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInformationServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserInformationReadMapper userInformationReadMapper;
    @Mock
    private UserInformationCreateMapper userInformationCreateMapper;
    @Mock
    private UserInformationRepository userInformationRepository;
    @InjectMocks
    private UserInformationService userInformationService;

    // findUserInformationByUserId
    @Test
    public void findUserInformationByUserIdSuccess() {
        // given
        when(userInformationRepository.findUserInformationByUserId(USER_ID_ONE)).thenReturn(Optional.of(USER_INFORMATION_ONE));
        when(userInformationReadMapper.map(USER_INFORMATION_ONE)).thenReturn(USER_INFORMATION_READ_DTO_ONE);

        // when
        UserInformationReadDto result = userInformationService.findUserInformationByUserId(USER_ID_ONE);

        // then
        assertThat(result).isEqualTo(USER_INFORMATION_READ_DTO_ONE);
        verify(userInformationRepository, times(TIMES_ONE)).findUserInformationByUserId(USER_ID_ONE);
        verify(userInformationReadMapper, times(TIMES_ONE)).map(USER_INFORMATION_ONE);
    }

    @Test
    public void findUserInformationByUserIdUserIdNotFoundThrowEntityNotFoundException() {
        // given
        when(userInformationRepository.findUserInformationByUserId(USER_ID_ONE)).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> userInformationService.findUserInformationByUserId(USER_ID_ONE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("UserInformation with user id: " + USER_ID_ONE + " not found");
        verify(userInformationRepository, times(TIMES_ONE)).findUserInformationByUserId(USER_ID_ONE);
    }


    // findUserInformationByOrderId
    @Test
    public void findUserInformationByOrderIdSuccess() {
        // given
        when(cartRepository.findCartByOrdersId(ORDER_ID_ONE)).thenReturn(Optional.of(CART));
        when(userRepository.findUserByCartId(CART_ID_ONE)).thenReturn(Optional.of(USER_ONE));
        when(userInformationRepository.findUserInformationByUserId(USER_ID_ONE)).thenReturn(Optional.of(USER_INFORMATION_ONE));
        when(userInformationReadMapper.map(USER_INFORMATION_ONE)).thenReturn(USER_INFORMATION_READ_DTO_ONE);

        // when
        UserInformationReadDto result = userInformationService.findUserInformationByOrderId(ORDER_ID_ONE);

        // then
        assertThat(result).isEqualTo(USER_INFORMATION_READ_DTO_ONE);
    }

    @Test
    public void findUserInformationByOrderIdCartNotFoundThrowEntityNotFoundException() {
        // given
        when(cartRepository.findCartByOrdersId(ORDER_ID_ONE)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userInformationService.findUserInformationByOrderId(ORDER_ID_ONE));

        // then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cart not found");
    }

    @Test
    public void findUserInformationByOrderIdUserNotFoundThrowEntityNotFoundException() {
        // given
        when(cartRepository.findCartByOrdersId(ORDER_ID_ONE)).thenReturn(Optional.of(CART));
        when(userRepository.findUserByCartId(CART.getId())).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userInformationService.findUserInformationByOrderId(ORDER_ID_ONE));

        // then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }


    // updateUserInformation
    @Test
    public void updateUserInformationSuccess() {
        // given
        when(userInformationRepository.findById(USER_INFORMATION_ID)).thenReturn(Optional.of(USER_INFORMATION_ONE));
        when(userInformationCreateMapper.map(USER_INFORMATION_CREATE_DTO_ONE, USER_INFORMATION_ONE)).thenReturn(USER_INFORMATION_ONE);
        when(userInformationRepository.saveAndFlush(USER_INFORMATION_ONE)).thenReturn(USER_INFORMATION_ONE);
        when(userInformationReadMapper.map(USER_INFORMATION_ONE)).thenReturn(USER_INFORMATION_READ_DTO_ONE);

        // when
        userInformationService.updateUserInformation(USER_INFORMATION_ID, USER_INFORMATION_CREATE_DTO_ONE);

        // then
        verify(userInformationRepository, times(TIMES_ONE)).findById(USER_INFORMATION_ID);
        verify(userInformationCreateMapper, times(TIMES_ONE)).map(USER_INFORMATION_CREATE_DTO_ONE, USER_INFORMATION_ONE);
        verify(userInformationRepository, times(TIMES_ONE)).saveAndFlush(USER_INFORMATION_ONE);
        verify(userInformationReadMapper, times(TIMES_ONE)).map(USER_INFORMATION_ONE);
    }

    @Test
    public void updateUserInformationUserInformationNotFoundThrowEntityNotFoundException() {
        // given
        when(userInformationRepository.findById(USER_INFORMATION_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userInformationService.updateUserInformation(USER_INFORMATION_ID, USER_INFORMATION_CREATE_DTO_ONE));

        // then
        assertThatThrownBy(() -> {
            throw throwable;
        }).isInstanceOf(EntityNotFoundException.class).hasMessage("UserInformation with id: " + USER_INFORMATION_ID + " not found");
    }

}