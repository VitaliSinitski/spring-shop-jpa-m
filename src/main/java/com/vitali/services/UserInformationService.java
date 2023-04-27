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
import org.webjars.NotFoundException;

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

//    @Transactional
//    public Optional<UserInformationReadDto> update(Integer id, UserInformationCreateDto userInformationCreateDto) {
//        return userInformationRepository.findById(id)
//                .map(userInformation -> userInformationCreateMapper.map(userInformationCreateDto, userInformation))
//                .map(userInformationRepository::saveAndFlush)
//                .map(userInformationReadMapper::map);
//    }

    public Optional<UserInformationReadDto> findUserInformationByUserId(Integer id) {
        return userInformationRepository.findUserInformationByUserId(id)
                .map(userInformationReadMapper::map);
    }

//    public Optional<UserInformationReadDto> findUserInformationByUserId(Integer userId) {
//        log.info("UserInformationService - findUserInformationByUserId - userId: {}", userId);
//        UserInformation userInformation = userInformationRepository.findAllByUserId(userId).orElse(null);
//        log.info("UserInformationService - findUserInformationByUserId - userInformation: {}", userInformation);
////        return userInformationReadMapper.map(userInformation);
//        return userInformationRepository.findUserInformationByUserId(userId)
//                .map(userInformationReadMapper::map);
//    }

    @Transactional
    public Optional<UserInformationReadDto> updateByUserId(Integer userId, UserInformationCreateDto userInformation) {
        return userInformationRepository.findUserInformationByUserId(userId)
                .map(userInform -> userInformationCreateMapper.map(userInformation, userInform))
                .map(userInformationRepository::saveAndFlush)
                .map(userInformationReadMapper::map);
    }

    @Transactional
    public Optional<UserInformationReadDto> update(Integer id, UserInformationCreateDto userInformationCreateDto) {
        log.info("UserInformationService - update - userInformation id: {}", id);
        log.info("UserInformationService - update - userInformationCreateDto: {}", userInformationCreateDto);
        Optional<UserInformation> userInformationOptional = userInformationRepository.findById(id);
        if (!userInformationOptional.isPresent()) {
            return Optional.empty();
        }

        UserInformation userInformation = userInformationOptional.get();
        log.info("UserInformationService - update - userInformation: {}", userInformation);


        UserInformation updateUserInformation = userInformationCreateMapper.map(userInformationCreateDto, userInformation);
        log.info("UserInformationService - update - updateUserInformation: {}", updateUserInformation);
        UserInformation userInformationSaved = userInformationRepository.saveAndFlush(userInformation);
        log.info("UserInformationService - update - userInformationSaved: {}", userInformationSaved);
        return Optional.of(userInformationReadMapper.map(userInformation));
    }

    @Transactional
    public UserInformation updateUserInformation(Integer userInformationId, UserInformationCreateDto userInformationCreateDto) {
        log.info("UserInformationService - update - userInformationId: {}", userInformationId);
        log.info("UserInformationService - update - userInformationCreateDto: {}", userInformationCreateDto);

        UserInformation userInformation = userInformationRepository.findById(userInformationId)
                .orElseThrow(() -> new NotFoundException("User information not found"));

        log.info("UserInformationService - update - before change - userInformation: {}", userInformation);

        // update user information fields
        userInformation.setFirstName(userInformationCreateDto.getFirstName());
        userInformation.setLastName(userInformationCreateDto.getLastName());
        userInformation.setPhone(userInformationCreateDto.getPhone());
        userInformation.setAddress(userInformationCreateDto.getAddress());
        userInformation.setBirthDate(userInformationCreateDto.getBirthDate());

        log.info("UserInformationService - update - after - change - userInformation: {}", userInformation);

        return userInformationRepository.save(userInformation);
    }

}
