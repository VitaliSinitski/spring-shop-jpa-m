package com.vitali.database.repositories;

import com.vitali.database.entities.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {
    Optional<UserInformation> findUserInformationByUserId(Integer id);
}
