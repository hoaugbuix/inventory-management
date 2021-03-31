package com.inventory.dev.dao;

import com.inventory.dev.entity.ProductInfoEntity;

import java.util.List;

public interface ProductInfoDAO<E> extends BaseDAO<E> {
    Integer saveJdbc(ProductInfoEntity productInfo);
    void updateJdbc(ProductInfoEntity productInfo);
    void deleteJdbc(int id);
    E findByIdJdbc(int id);
    List<E> findAllJdbc();
}
