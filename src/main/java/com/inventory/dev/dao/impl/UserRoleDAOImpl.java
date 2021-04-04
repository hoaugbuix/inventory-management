package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.UserRoleDAO;
import com.inventory.dev.entity.UserRoleEntity;
import com.inventory.dev.model.mapper.UserRoleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRoleDAOImpl extends BaseDAOImpl<UserRoleEntity> implements UserRoleDAO<UserRoleEntity> {
    @Override
    public Integer saveUserRoleJdbc(UserRoleEntity userRole) {
        StringBuilder sql = new StringBuilder("INSERT INTO user_role ");
        sql.append("(user_id, role_id, active_flag, created_date, updated_date)");
        sql.append(" VALUES (?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(), userRole.getUsers(), userRole.getRoles(),
                userRole.getActiveFlag(), userRole.getCreatedDate(), userRole.getUpdatedDate());
    }

    @Override
    public void updateUserRoleJdbc(UserRoleEntity instance) {

    }

    @Override
    public void deleteUserRoleJdbc(int id) {
        String sql = "UPDATE user_role SET active_flag = 0, updated_date = now() WHERE id = ?";
        updateJdbc(sql,id);
    }

    @Override
    public List<UserRoleEntity> findAllUserRoleJdbc() {
        String sql = "select * from user_role where active_flag = 1";
        return queryJdbc(sql, new UserRoleMapper());
    }

    @Override
    public UserRoleEntity findUserRoleById(int id) {
        String sql = "select * from user_role where id = ?";
        List<UserRoleEntity> userRole = queryJdbc(sql,new UserRoleMapper(), id);
        return userRole.isEmpty() ? null : userRole.get(0);
    }

    @Override
    public UserRoleEntity findUserRoleByUserIdAndRoleId(int userId, int roleId) {
        String sql = "SELECT * FROM user_role where user_id = ? and role_id = ?";
        List<UserRoleEntity> userRole = queryJdbc(sql,new UserRoleMapper(), userId, roleId);
        return userRole.isEmpty() ? null : userRole.get(0);
    }
}
