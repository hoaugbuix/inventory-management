package com.inventory.dev.dao;

import java.util.List;

public interface MenuDAO<E> extends BaseDAO<E> {
    List<E> getAllMenuJdbc();
    E findOneMenuJdbc(int id);
    Integer saveMenuJdbc(E instance);
    void updateMenuJdbc(E instance);
}
