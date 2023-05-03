package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import com.vitali.mappers.userInformation.UserInformationCreateMapper;
import com.vitali.mappers.userInformation.UserInformationReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserInformationRepository userInformationRepository;
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
    private final UserInformationCreateMapper userInformationCreateMapper;
    private final UserInformationReadMapper userInformationReadMapper;
    private final CartRepository cartRepository;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

//    public Integer create(UserCreateDto userCreateDto) {
//        User userEntity = userCreateMapper.map(userCreateDto);
////        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//        return userRepository.save(userEntity).getId();
//    }

//    public Optional<UserReadDto> findById(Integer id) {
//        return userRepository.findById(id)
//                .map(userReadMapper::map);
//    }

    public UserReadDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto, UserInformationCreateDto userInformationCreateDto) {
        Cart cart = Cart.builder().createdDate(LocalDateTime.now()).build();
        UserInformation userInformation = Optional.of(userInformationCreateDto)
                .map(userInformationCreateMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation not found"));
        User user = Optional.of(userCreateDto)
                .map(userCreateMapper::map)
                .map(userRepository::save)
//                .map(userRepository::saveAndFlush) // todo variant???
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        cart.setUser(user);
        userInformation.setUser(user);
//        user.setCart(cart);
        cartRepository.save(cart);
        userInformationRepository.save(userInformation);
        return userReadMapper.map(user);
    }

    @Transactional
    public UserReadDto updatePersonalInfo(Integer userId, Integer userInformationId, UserCreateDto userCreateDto, UserInformationCreateDto userInformationCreateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
        UserInformation userInformation = userInformationRepository.findById(userInformationId)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation with id: " + userInformationId + " not found"));









        userInformation.setUser(user);
//        user.setCart(cart);
        userInformationRepository.save(userInformation);
        return userReadMapper.map(user);
    }

    // It is function correctly
//    @Transactional
//    public Optional<UserReadDto> update(Integer id, UserCreateDto userCreateDto) {
//        return userRepository.findById(id)
//                .map(user -> userCreateMapper.map(userCreateDto, user))
//                .map(userRepository::saveAndFlush)
//                .map(userReadMapper::map);
//    }

//    @Transactional
//    public Optional<UserReadDto> update(Integer id, UserCreateDto userCreateDto) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    User updatedUser = userCreateMapper.map(userCreateDto, user);
//                    return userRepository.saveAndFlush(updatedUser);
//                })
//                .map(userReadMapper::map)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//    }

    @Transactional
    public UserReadDto update(Integer id, UserCreateDto userCreateDto) {
        log.info("UserService - update - userCreateDto: {}", userCreateDto);
        return userRepository.findById(id)
                .map(user -> userCreateMapper.map(userCreateDto, user))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

//    @Transactional
//    public Optional<UserReadDto> update(Integer id, UserCreateDto userCreateDto) {
//        return userRepository.findById(id)
//                .map(user -> userCreateMapper.map(userCreateDto, user))
//                .map(userRepository::saveAndFlush)
//                .map(userReadMapper::map)
//                .map(Optional::ofNullable)
//                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
//    }


    // It is function correctly
    @Transactional
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }


    public String getCurrentUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public UserReadDto getCurrentUserByUsernameFromSecurityContext() {
        String username = getCurrentUsernameFromSecurityContext();
        if (username == null || !username.isEmpty()) {
            return userRepository.findUserByUsername(username)
                    .map(userReadMapper::map)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        }
        return null;
    }

}

