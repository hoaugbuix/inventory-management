package com.inventory.dev.service;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.model.request.CreateUserReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> findByProperty(String property, Object value);

    UserEntity findById(int id);

    UserEntity createUser(CreateUserReq req);

    void save(UserEntity user);

    void update(UserEntity users);

    void deleteUser(UserEntity user);

    List<UserEntity> getUsersList(UserEntity users, Paging paging);

    // Jdbc
//    void insert(UserEntity user);
//
//    void updateUser(UserEntity user);
//
//    void deleteUser(int id);
//
//    List<UserEntity> findAll();
//
//    UserEntity getUserById(int id);

}
