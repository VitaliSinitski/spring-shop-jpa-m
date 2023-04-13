package com.vitali.services;

import com.vitali.dto.user.UserReadDto;
import com.vitali.entities.User;
import com.vitali.dto.user.UserCreateDto;

import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
import com.vitali.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
//    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

    public Integer create(UserCreateDto userCreateDto) {
        User userEntity = userCreateMapper.map(userCreateDto);
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity).getId();
    }

    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

//        @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        user.getAuthorities()
////                        user.getAuthorities()
//                ))
//                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
//    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByEmail(username)
//                .map(user -> new AdaptedUserDetails(
//                        user.getId(),
//                        user.getUsername(),
//                        user.getPassword(),
//                        Collections.singleton(user.getRole())
//                )).orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
//    }
}

