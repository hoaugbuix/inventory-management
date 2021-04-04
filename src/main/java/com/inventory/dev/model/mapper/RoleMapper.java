package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserRoleEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<RoleEntity> {
    @Override
    public RoleEntity mapRow(ResultSet resultSet) {
        try {
            RoleEntity role = new RoleEntity();

            role.setId(resultSet.getInt("id"));
            role.setRoleName(resultSet.getString("role_name"));
            role.setDescription(resultSet.getString("description"));
            role.setActiveFlag(resultSet.getInt("active_flag"));
            role.setCreatedDate(resultSet.getDate("created_date"));
            role.setUpdatedDate(resultSet.getDate("updated_date"));
            return role;
        }catch (SQLException e) {
            return null;
        }
    }
}
