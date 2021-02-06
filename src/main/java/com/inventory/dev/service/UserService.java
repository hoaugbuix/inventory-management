package com.inventory.dev.service;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.model.request.CreateUserReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<UserEntity> findByProperty(String property, Object value);

    public UserEntity findById(int id);

    public UserEntity createUser(CreateUserReq req);

    public void save(UserEntity user);

    public void update(UserEntity users);

    public void deleteUser(UserEntity user);

    public List<UserEntity> getUsersList(UserEntity users, Paging paging);
}
