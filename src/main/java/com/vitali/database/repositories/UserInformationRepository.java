package com.vitali.database.repositories;

import com.vitali.database.entities.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {
}
