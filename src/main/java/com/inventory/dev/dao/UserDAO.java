package com.inventory.dev.dao;

import java.util.List;

public interface UserDAO<E> extends BaseDAO<E> {
    Integer saveUserJdbc(E user);

    void updateUserJdbc(E user);

    void deleteUserJdbc(int id);

    List<E> findAllUserJdbc();

    E getUserByIdJdbc(int id);

    E getUserByEmailAndUsernameJdbc(String email, String username);

    E getUserByEmailJdbc(String email);
}
