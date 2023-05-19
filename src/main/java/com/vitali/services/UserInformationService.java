package com.vitali.services;

import com.vitali.database.entities.Cart;
import com.vitali.database.entities.User;
import com.vitali.database.repositories.CartRepository;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.database.repositories.UserRepository;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.mappers.userInformation.UserInformationCreateMapper;
import com.vitali.mappers.userInformation.UserInformationReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInformationService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserInformationReadMapper userInformationReadMapper;
    private final UserInformationCreateMapper userInformationCreateMapper;
    private final UserInformationRepository userInformationRepository;

    public UserInformationReadDto findUserInformationByUserId(Integer id) {
        return userInformationRepository.findUserInformationByUserId(id)
                .map(userInformationReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation with user id: " + id + " not found"));
    }

    public UserInformationReadDto findUserInformationByOrderId(Integer orderId) {
        Cart cart = cartRepository.findCartByOrdersId(orderId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        Integer cartId = cart.getId();
        User user = userRepository.findUserByCartId(cartId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Integer userId = user.getId();
        return userInformationRepository.findUserInformationByUserId(userId)
                .map(userInformationReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation with user id: " + userId + " not found"));
    }

    @Transactional
    public void updateUserInformation(Integer userInformationId, UserInformationCreateDto userInformationCreateDto) {
        userInformationRepository.findById(userInformationId)
                .map(userInformation -> userInformationCreateMapper.map(userInformationCreateDto, userInformation))
                .map(userInformationRepository::saveAndFlush)
                .map(userInformationReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation with id: " + userInformationId + " not found"));
    }
}
