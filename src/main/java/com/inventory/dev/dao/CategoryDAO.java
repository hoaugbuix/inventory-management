package com.inventory.dev.dao;

import java.util.List;

public interface CategoryDAO<E> extends BaseDAO<E> {
    List<E> findAll();
    E findOne(int id);
    E findByCode(String code);
    Integer saveCategory(E instance);
    void updateCategory(E instance);
    void deleteCategory(int id);
}
