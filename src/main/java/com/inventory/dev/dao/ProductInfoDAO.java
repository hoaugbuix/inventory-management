package com.inventory.dev.dao;

import com.inventory.dev.entity.ProductInfoEntity;

import java.util.List;

public interface ProductInfoDAO<E> extends BaseDAO<E> {
    Integer saveProductInfoJdbc(ProductInfoEntity productInfo);
    void updateProductInfoJdbc(ProductInfoEntity productInfo);
    void deleteProductInfoJdbc(int id);
    E findProductInfoByIdJdbc(int id);
    List<E> findAllProductInfoJdbc();
}
