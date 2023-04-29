package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.mappers.user.UserCreateMapper;
import com.vitali.mappers.user.UserReadMapper;
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

//    public Optional<UserReadDto> findById(Integer id) {
//        return userRepository.findById(id)
//                .map(userReadMapper::map);
//    }

    public UserReadDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id: " + id + " not found"));
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
    public Optional<UserReadDto> update(Integer id, UserCreateDto userCreateDto) {
        return userRepository.findById(id)
                .map(user -> userCreateMapper.map(userCreateDto, user))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .map(Optional::ofNullable)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    // It is function correctly
    @Transactional
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


    public String getCurrentUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public UserReadDto getCurrentUserByUsernameFromSecurityContext() {
        String username = getCurrentUsernameFromSecurityContext();
        if (username == null || !username.isEmpty()) {
            return userRepository.findUserByUsername(username)
                    .map(userReadMapper::map).orElse(null);
        }
        return null;
    }


//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails userDetails) {
//                return getUserIdFromUserDetails(userDetails);
//            }
//        }
//        return null;
//    }
//
//    private Long getUserIdFromUserDetails(UserDetails userDetails) {
//        // Retrieve the user id from your user details object
//        // For example, if your user details object has a getId() method:
//        return userDetails.getId();
//    }


}

