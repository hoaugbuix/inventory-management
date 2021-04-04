package com.inventory.dev.dao;

import java.util.List;

public interface RoleDAO<E> extends BaseDAO<E> {
    List<E> findAllRoleJdbc();

    Integer saveRoleJdbc(E instance);

    void updateRoleJdbc(E instance);

    void deleteRoleJdbc(int id);

    E findRoleByRoleNameJdbc(String roleName);

    E findRoleByIdJdbc(int id);
}
