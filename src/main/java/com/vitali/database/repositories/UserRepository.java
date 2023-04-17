package com.vitali.database.repositories;

import com.vitali.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    boolean existsUserByEmail(String email);
}
