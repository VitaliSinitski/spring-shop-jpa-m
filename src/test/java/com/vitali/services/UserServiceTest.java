package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.entities.enums.Role;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import com.vitali.mappers.userInformation.UserInformationCreateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserInformationRepository userInformationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCreateMapper userCreateMapper;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private UserInformationCreateMapper userInformationCreateMapper;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private UserService userService;

    // findAll
    @Test
    public void findAllSuccess() {
        // given
        when(userRepository.findAll()).thenReturn(USER_LIST);
        when(userReadMapper.map(USER_ONE)).thenReturn(USER_READ_DTO_ONE);
        when(userReadMapper.map(USER_TWO)).thenReturn(USER_READ_DTO_TWO);

        // when
        List<UserReadDto> result = userService.findAll();

        // then
        assertThat(result).hasSize(SIZE_TWO);
        assertThat(result.get(0)).isEqualTo(USER_READ_DTO_ONE);
        assertThat(result.get(1)).isEqualTo(USER_READ_DTO_TWO);
        assertThat(result).isEqualTo(USER_READ_DTO_LIST);
    }

    // findBy
    @Test
    public void findByIdSuccess() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.of(USER));
        when(userReadMapper.map(USER)).thenReturn(USER_READ_DTO);

        // when
        UserReadDto result = userService.findById(USER_ID_ONE);

        // then
        assertThat(result).isEqualTo(USER_READ_DTO);
    }

    @Test
    public void findByIdUserNotFoundThrowEntityNotFoundException() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.empty());

        // when + than
        assertThatThrownBy(() -> userService.findById(USER_ID_ONE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with id: " + USER_ID_ONE + " not found");
        verify(userRepository, times(TIMES_ONE)).findById(USER_ID_ONE);
    }


    // create
    @Test
    public void createSuccess() {
        // given
        when(userCreateMapper.map(any(UserCreateDto.class))).thenReturn(USER);
        when(userRepository.save(any(User.class))).thenReturn(USER);
        when(userInformationCreateMapper.map(any(UserInformationCreateDto.class))).thenReturn(USER_INFORMATION);
        when(userInformationRepository.save(any(UserInformation.class))).thenReturn(USER_INFORMATION);
        when(userReadMapper.map(any(User.class))).thenReturn(USER_READ_DTO);

        // when
        UserReadDto result = userService.create(USER_CREATE_DTO, USER_INFORMATION_CREATE_DTO);

        // then
        assertThat(result).isEqualTo(USER_READ_DTO);
        verify(userCreateMapper, times(TIMES_ONE)).map(USER_CREATE_DTO);
        verify(userRepository, times(TIMES_ONE)).save(USER);
        verify(userInformationCreateMapper, times(TIMES_ONE)).map(USER_INFORMATION_CREATE_DTO);
        verify(userInformationRepository, times(TIMES_ONE)).save(USER_INFORMATION);
        verify(userReadMapper, times(TIMES_ONE)).map(USER);
    }

    @Test
    public void createUserNotSaved() {
        // given
        when(userCreateMapper.map(USER_CREATE_DTO)).thenReturn(USER);
        when(userInformationCreateMapper.map(USER_INFORMATION_CREATE_DTO)).thenReturn(USER_INFORMATION);
        when(userRepository.save(USER)).thenReturn(null); // throwing exception

        // when
        Throwable throwable = catchThrowable(() -> userService.create(USER_CREATE_DTO, USER_INFORMATION_CREATE_DTO));

        // then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class).hasMessage("User not found");
        verify(userCreateMapper, times(1)).map(USER_CREATE_DTO);
        verify(userInformationCreateMapper, times(1)).map(USER_INFORMATION_CREATE_DTO);
        verify(userRepository, times(1)).save(USER);
        verify(cartRepository, never()).save(any(Cart.class));
        verify(userInformationRepository, never()).save(any(UserInformation.class));
    }

    @Test
    public void createUserInformationCreateDtoNotFoundThrowEntityNotFoundException() {
        // given
        UserInformationCreateDto userInformationCreateDto = null;

        // when
        Throwable throwable = catchThrowable(() -> userService.create(USER_CREATE_DTO, userInformationCreateDto));

        // then
        assertThat(throwable)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("UserInformation not found");
    }


    // update
    @Test
    public void updateSuccess() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.of(USER));
        when(userCreateMapper.map(USER_CREATE_DTO, USER)).thenReturn(USER);
        when(userRepository.saveAndFlush(USER)).thenReturn(USER);
        when(userReadMapper.map(USER)).thenReturn(USER_READ_DTO);

        // when
        UserReadDto result = userService.update(USER_ID_ONE, USER_CREATE_DTO);

        // then
        assertThat(result).isEqualTo(USER_READ_DTO);
        assertThat(USER_READ_DTO.getId()).isEqualTo(USER_ID_ONE);
        assertThat(USER_READ_DTO.getUsername()).isEqualTo(USER_NAME);
        assertThat(USER_READ_DTO.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(USER_READ_DTO.getRole()).isEqualTo(Role.USER);
        assertThat(USER_READ_DTO.isEnabled()).isEqualTo(true);
        verify(userRepository, times(TIMES_ONE)).findById(USER_ID_ONE);
        verify(userCreateMapper, times(TIMES_ONE)).map(USER_CREATE_DTO, USER);
        verify(userRepository, times(TIMES_ONE)).saveAndFlush(USER);
        verify(userReadMapper, times(TIMES_ONE)).map(USER);
    }

    @Test
    public void updateUserNotFoundThrowEntityNotFoundException() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.update(USER_ID_ONE, USER_CREATE_DTO));

        // then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("User with id: 1 not found");
        verify(userRepository, times(TIMES_ONE)).findById(USER_ID_ONE);
        verifyNoMoreInteractions(userCreateMapper, userRepository, userReadMapper);
    }

    @Test
    void updateThrowExceptionRepositoryThrowsException() {
        // given
        UserCreateDto userCreateDto = UserCreateDto.builder().build();

        // when
        when(userRepository.findById(USER_ID_ONE)).thenThrow(new RuntimeException());

        // then
        Throwable throwable = catchThrowable(() -> userService.update(USER_ID_ONE, userCreateDto));
        assertThat(throwable).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void updateThrowRuntimeExceptionSaveFails() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.of(USER));
        when(userCreateMapper.map(USER_CREATE_DTO, USER)).thenReturn(USER);
        when(userRepository.saveAndFlush(USER)).thenThrow(new RuntimeException());

        // when
        Throwable throwable = catchThrowable(() -> userService.update(USER_ID_ONE, USER_CREATE_DTO));

        // then
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        verify(userRepository, times(TIMES_ONE)).findById(USER_ID_ONE);
        verify(userCreateMapper, times(TIMES_ONE)).map(USER_CREATE_DTO, USER);
        verify(userRepository, times(TIMES_ONE)).saveAndFlush(USER);
        verifyNoMoreInteractions(userReadMapper);
    }


    // updatePassword
    @Test
    public void updatePasswordSuccess() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.of(USER));
        when(userCreateMapper.mapPassword(USER_CREATE_DTO, USER)).thenReturn(USER);
        when(userRepository.saveAndFlush(USER)).thenReturn(USER);
        when(userReadMapper.map(USER)).thenReturn(USER_READ_DTO);

        // when
        userService.updatePassword(USER_ID_ONE, USER_CREATE_DTO);

        // then
        verify(userRepository, times(TIMES_ONE)).findById(USER_ID_ONE);
        verify(userCreateMapper, times(TIMES_ONE)).mapPassword(USER_CREATE_DTO, USER);
        verify(userRepository, times(TIMES_ONE)).saveAndFlush(USER);
        verify(userReadMapper, times(TIMES_ONE)).map(USER);
    }

    @Test
    public void updatePasswordUserNotFoundThrowEntityNotFoundException() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.updatePassword(USER_ID_ONE, USER_CREATE_DTO));

        // then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with id: " + USER_ID_ONE + " not found");
    }


    // delete
    @Test
    public void deleteSuccess() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.of(USER));

        // when
        boolean result = userService.delete(USER_ID_ONE);

        // then
        assertTrue(result);
        verify(userRepository).delete(USER);
    }

    @Test
    public void deleteUserNotFoundThrowEntityNotFoundException() {
        // given
        when(userRepository.findById(USER_ID_ONE)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.delete(USER_ID_ONE));

        // then
        assertThat(throwable).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with id: " + USER_ID_ONE + " not found");
        verify(userRepository, never()).delete(any());
    }

    // loadUserByUsername
    @Test
    public void loadUserByUsernameSuccess() {
        //given
        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword("$2a$10$3.8jcqj1Uf3xRHdW3/dNH.bIvYHikSh4ht5L.3T1sNwKp3rUuqB7S"); //password is "password"
        user.setRole(Role.USER);

        when(userRepository.findUserByUsername(USER_NAME)).thenReturn(Optional.of(user));

        //when
        UserDetails userDetails = userService.loadUserByUsername(USER_NAME);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(USER_NAME);
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsOnly("USER");
    }

    @Test
    public void loadUserByUsernameUsernameNotFoundThrowUsernameNotFoundException() {
        // given
        String invalidUsername = "invalidUsername";
        when(userRepository.findUserByUsername(invalidUsername)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.loadUserByUsername(invalidUsername));

        // then
        assertThat(throwable).isInstanceOf(UsernameNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("User with username: " + invalidUsername + " not found");
    }

}