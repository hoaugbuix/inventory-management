package com.inventory.dev.service;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<RoleEntity> getRoleList(RoleEntity role, Paging paging);

    void saveRole(RoleEntity role) throws Exception;

    void updateRole(RoleEntity role) throws Exception;

    void deleteRole(RoleEntity role) throws Exception;

    List<RoleEntity> findRole(String property, Object value);

    RoleEntity findByIdRole(int id);
}
