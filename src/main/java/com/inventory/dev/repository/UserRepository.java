package com.inventory.dev.repository;


import com.inventory.dev.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByEmailAndUsername(String email, String username);

    public Optional<UserEntity> findByEmail(String email);

    public Optional<UserEntity> findByUsernameOrEmail(String username, String email);
}
