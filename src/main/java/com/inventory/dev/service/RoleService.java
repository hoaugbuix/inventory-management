package com.inventory.dev.service;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    public List<RoleEntity> getRoleList(RoleEntity role, Paging paging);

    public void saveRole(RoleEntity role) throws Exception;

    public void updateRole(RoleEntity role) throws Exception;

    public void deleteRole(RoleEntity role) throws Exception;

    public List<RoleEntity> findRole(String property, Object value);

    public RoleEntity findByIdRole(long id);
}
