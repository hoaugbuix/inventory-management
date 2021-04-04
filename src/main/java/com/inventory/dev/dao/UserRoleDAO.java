package com.inventory.dev.dao;

import java.util.List;

public interface UserRoleDAO<E> extends BaseDAO<E> {
    Integer saveUserRoleJdbc(E instance);
    void updateUserRoleJdbc(E instance);
    void deleteUserRoleJdbc(int id);
    List<E> findAllUserRoleJdbc();
    E findUserRoleById(int id);
    E findUserRoleByUserIdAndRoleId(int userId, int roleId);
}
