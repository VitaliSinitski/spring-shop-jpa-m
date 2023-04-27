package com.vitali.services;

import com.vitali.database.entities.UserInformation;
import com.vitali.database.repositories.UserInformationRepository;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.mappers.userInformation.UserInformationCreateMapper;
import com.vitali.mappers.userInformation.UserInformationReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInformationService {
    private final UserInformationReadMapper userInformationReadMapper;
    private final UserInformationCreateMapper userInformationCreateMapper;
    private final UserInformationRepository userInformationRepository;

    public List<UserInformationReadDto> findAll() {
        return userInformationRepository.findAll().stream()
                .map(userInformationReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<UserInformationReadDto> update(Integer id, UserInformationCreateDto userInformationCreateDto) {
        return userInformationRepository.findById(id)
                .map(userInformation -> userInformationCreateMapper.map(userInformationCreateDto, userInformation))
                .map(userInformationRepository::saveAndFlush)
                .map(userInformationReadMapper::map);
    }

    public Optional<UserInformationReadDto> findUserInformationByUserId(Integer id) {
        return userInformationRepository.findUserInformationByUserId(id)
                .map(userInformationReadMapper::map);
    }

    @Transactional
    public Optional<UserInformationReadDto> updateByUserId(Integer userId, UserInformationCreateDto userInformation) {
        return userInformationRepository.findUserInformationByUserId(userId)
                .map(userInform -> userInformationCreateMapper.map(userInformation, userInform))
                .map(userInformationRepository::saveAndFlush)
                .map(userInformationReadMapper::map);
    }
}
