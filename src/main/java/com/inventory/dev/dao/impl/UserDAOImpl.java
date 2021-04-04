package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.UserDAO;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.model.mapper.UserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<UserEntity> implements UserDAO<UserEntity> {
    @Override
    public Integer saveUserJdbc(UserEntity user) {
        StringBuilder sql = new StringBuilder("INSERT INTO user ");
        sql.append("(first_name, last_name, avatar, user_name, password, email, active_flag, created_date, updated_date)");
        sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(), user.getFirstName(), user.getLastName(),
                user.getAvatar(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getActiveFlag(), user.getCreatedDate(), user.getUpdatedDate());
    }

    @Override
    public void updateUserJdbc(UserEntity user) {
        StringBuilder sql = new StringBuilder("UPDATE user SET");
        sql.append("first_name = ?, last_name = ?, avatar = ?," +
                " user_name = ?, password = ?, email = ?, active_flag = ?, created_date = ?, updated_date = ?");
        updateJdbc(sql.toString(), user.getFirstName(), user.getLastName(),
                user.getAvatar(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getActiveFlag(), user.getCreatedDate(), user.getUpdatedDate());
    }

    @Override
    public void deleteUserJdbc(int id) {
        String sql = "UPDATE user SET active_flag = 0, updated_date = now() WHERE id = ?";
        updateJdbc(sql,id);
    }

    @Override
    public List<UserEntity> findAllUserJdbc() {
        String sql = "select * from user";
        return queryJdbc(sql, new UserMapper());
    }

    @Override
    public UserEntity getUserByIdJdbc(int id) {
        String sql = "select * from user where id = ?";
        List<UserEntity> user = queryJdbc(sql, new UserMapper(), id);
        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public UserEntity getUserByEmailAndUsernameJdbc(String email, String username) {
        String sql = "select * from user where email = ? and user_name = ?";
        List<UserEntity> user = queryJdbc(sql, new UserMapper(), email, username);
        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public UserEntity getUserByEmailJdbc(String email) {
        String sql = "select * from user where email = ?";
        List<UserEntity> user = queryJdbc(sql, new UserMapper(), email);
        return user.isEmpty() ? null : user.get(0);
    }
}
