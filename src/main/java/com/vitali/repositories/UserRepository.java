package com.vitali.repositories;

import com.vitali.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//    User findByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    boolean existsUserByEmail(String email);
}
