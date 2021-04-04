package com.inventory.dev.dao;

import java.util.List;

public interface CategoryDAO<E> extends BaseDAO<E> {
    List<E> findAllCategoryJdbc();
    E findOneCategoryJdbc(int id);
    E findCategoryByCodeJdbc(String code);
    Integer saveCategoryJdbc(E instance);
    void updateCategoryJdbc(E instance);
    void deleteCategoryJdbc(int id);
}
