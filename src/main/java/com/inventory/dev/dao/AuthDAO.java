package com.inventory.dev.dao;


import com.inventory.dev.entity.AuthEntity;

public interface AuthDAO<E> extends BaseDAO<E> {
    AuthEntity find(int roleId, int menuId);
}
