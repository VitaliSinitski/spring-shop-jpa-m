package com.vitali.services;

import com.vitali.dto.user.UserReadDto;
import com.vitali.entities.Cart;
import com.vitali.entities.User;
import com.vitali.dto.user.UserCreateDto;

import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import com.vitali.repositories.CartRepository;
import com.vitali.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
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

    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto) {
        Cart cart = Cart.builder().createdDate(LocalDateTime.now()).build();
        User user = Optional.of(userCreateDto)
                .map(userCreateMapper::map)
                .map(userRepository::save)
//                .map(userRepository::saveAndFlush) // todo variant???
                .orElseThrow();
        cart.setUser(user);
//        user.setCart(cart);
        cartRepository.save(cart);
        return userReadMapper.map(user);

//        return Optional.of(userCreateDto)
//                .map(userCreateMapper::map)
//                .map(userRepository::save)
//                .map(userReadMapper::map)
//                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Integer id, UserCreateDto userCreateDto) {
        return userRepository.findById(id)
                .map(user -> userCreateMapper.map(userCreateDto, user))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                )).orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

}

