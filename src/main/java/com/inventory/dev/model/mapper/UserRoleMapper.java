package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.entity.UserRoleEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleMapper implements RowMapper<UserRoleEntity> {
    @Override
    public UserRoleEntity mapRow(ResultSet resultSet) {
        try {
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setId(resultSet.getInt("id"));
            userRole.setActiveFlag(resultSet.getInt("active_flag"));
            userRole.setCreatedDate(resultSet.getDate("created_date"));
            userRole.setUpdatedDate(resultSet.getDate("updated_date"));
            try {
                UserEntity user = new UserEntity();
                RoleEntity role = new RoleEntity();
                userRole.setUsers(user);
                userRole.setRoles(role);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return userRole;
        }catch (SQLException e) {
            return null;
        }
    }
}
