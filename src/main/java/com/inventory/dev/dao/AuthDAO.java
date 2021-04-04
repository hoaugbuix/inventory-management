package com.inventory.dev.dao;


import com.inventory.dev.entity.AuthEntity;

public interface AuthDAO<E> extends BaseDAO<E> {
    AuthEntity find(int roleId, int menuId);

    //jdbc
    E findAuthByRoleIdAndMenuIdJdbc(int roleId, int menuId);
    Integer SaveAuthJdbc(E instance);
    void updateAuthJdbc(E instance);
}
