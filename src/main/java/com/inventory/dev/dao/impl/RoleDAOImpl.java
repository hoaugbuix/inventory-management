package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.RoleDAO;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.model.mapper.RoleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleDAOImpl extends BaseDAOImpl<RoleEntity> implements RoleDAO<RoleEntity> {
    @Override
    public Integer saveRoleJdbc(RoleEntity role) {
        StringBuilder sql = new StringBuilder("INSERT INTO role (role_name, description, active_flag, created_date, updated_date)");
        sql.append(" VALUES(?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(),role.getRoleName(), role.getDescription(),
                role.getActiveFlag(), role.getCreatedDate(), role.getUpdatedDate());
    }

    @Override
    public void updateRoleJdbc(RoleEntity role) {
        StringBuilder sql = new StringBuilder("UPDATE category SET role_name = ?, description = ?, active_flag = ?, created_date = ?, updated_date = ?");
        updateJdbc(sql.toString(), role.getRoleName(), role.getDescription(), role.getActiveFlag(), role.getCreatedDate(), role.getUpdatedDate());
    }

    @Override
    public void deleteRoleJdbc(int id) {
        String sql = "UPDATE role SET active_flag = 0, updated_date = now() WHERE id = ?";
        updateJdbc(sql,id);
    }

    @Override
    public List<RoleEntity> findAllRoleJdbc() {
        String sql = "select * from role";
        return queryJdbc(sql, new RoleMapper());
    }

    @Override
    public RoleEntity findRoleByRoleNameJdbc(String roleName) {
        String sql = "select * from role where role_name = ?";
        List<RoleEntity> role = queryJdbc(sql,new RoleMapper(), roleName);
        return role.isEmpty() ? null : role.get(0);
    }

    @Override
    public RoleEntity findRoleByIdJdbc(int id) {
        String sql = "select * from role where id = ?";
        List<RoleEntity> role = queryJdbc(sql,new RoleMapper(), id);
        return role.isEmpty() ? null : role.get(0);
    }
}
