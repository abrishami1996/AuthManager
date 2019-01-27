package com.authenticationmanager.authmanager.repository;

import com.authenticationmanager.authmanager.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String Username,String Password);
    User findByUsername(String Username);
    boolean existsByUsername(String username);

    @Transactional
    void deleteByUsername(String Username);
}
