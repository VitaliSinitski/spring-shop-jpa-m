package com.vitali.integration.service;

import com.vitali.database.entities.User;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.user.UserReadDto;
import com.vitali.integration.annotation.IT;
import com.vitali.services.UserService;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.vitali.util.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class UserServiceIT {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Test
    public void findAll() {

        // when
        List<UserReadDto> users = userService.findAll();

        // then
        assertThat(users).isNotNull();
        assertThat(users).hasSize(SIZE_FIVE);
    }

    @Test
    public void findAllUsersEqualsToRepository() {
        // given
        List<User> expectedUsers = userRepository.findAll();

        // when
        List<UserReadDto> users = userService.findAll();

        // then
        assertThat(users).isNotEmpty();
        assertThat(users).hasSameSizeAs(expectedUsers);
        assertThat(users).extracting(UserReadDto::getUsername)
                .containsExactlyInAnyOrderElementsOf(expectedUsers.stream().map(User::getUsername).collect(Collectors.toList()));
    }

    @Test
    public void findById() {
        // given
        User expectedUser = userRepository.findAll().get(ZERO);

        // when
        UserReadDto user = userService.findById(expectedUser.getId());

        // then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(expectedUser.getId());
        assertThat(user.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(user.getRole()).isEqualTo(expectedUser.getRole());
        assertThat(user.isEnabled()).isEqualTo(expectedUser.isEnabled());
    }


    @Test
    public void create() {
        // when
        UserReadDto user = userService.create(USER_CREATE_DTO, USER_INFORMATION_CREATE_DTO);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USER_CREATE_DTO.getUsername());
        assertThat(user.getEmail()).isEqualTo(USER_CREATE_DTO.getEmail());
        assertThat(user.getRole()).isEqualTo(USER_CREATE_DTO.getRole());
        assertThat(user.isEnabled()).isEqualTo(USER_CREATE_DTO.getEnabled());
    }

    @Test
    public void update() {
        //given
        User user = userRepository.saveAndFlush(USER_NO_ID);

        // when
        UserReadDto updatedUser = userService.update(user.getId(), USER_CREATE_DTO_MODIFIED);

        // then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(user.getId());
        assertThat(updatedUser.getUsername()).isEqualTo(USER_CREATE_DTO_MODIFIED.getUsername());
        assertThat(updatedUser.getEmail()).isEqualTo(USER_CREATE_DTO_MODIFIED.getEmail());
        assertThat(updatedUser.getRole()).isEqualTo(USER_CREATE_DTO_MODIFIED.getRole());
        assertThat(updatedUser.isEnabled()).isEqualTo(USER_CREATE_DTO_MODIFIED.getEnabled());
    }

    @Test
    public void updatePassword() {
        // given
        USER_NO_PASSWORD.setPassword(passwordEncoder.encode(USER_PASSWORD));
        User user = userRepository.save(USER_NO_PASSWORD);

        // when
        userService.updatePassword(user.getId(), USER_CREATE_DTO_PASSWORD_NEW);

        // then
        User updatedUser = userRepository.findById(user.getId()).get();
        assertThat(passwordEncoder.matches(USER_PASSWORD_NEW, updatedUser.getPassword())).isTrue();
    }

    @Test
    public void delete() {
        // given
        User expectedUser = userRepository.findAll().get(ZERO);

        // when
        boolean deleted = userService.delete(expectedUser.getId());

        // then
        assertThat(deleted).isTrue();
        assertThat(userRepository.findById(expectedUser.getId())).isEmpty();
    }

    @Test
    public void loadUserByUsername() {
        // given
        User expectedUser = userRepository.findAll().get(ZERO);
        String username = expectedUser.getUsername();

        // when
        UserDetails userDetails = userService.loadUserByUsername(username);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(userDetails.getAuthorities())
                .extracting(AUTHORITY)
                .containsExactly(expectedUser.getRole().toString());
    }

}