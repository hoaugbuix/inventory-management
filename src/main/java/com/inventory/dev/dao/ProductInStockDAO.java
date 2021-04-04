package com.inventory.dev.dao;

import java.util.List;

public interface ProductInStockDAO<E> extends BaseDAO<E> {
    List<E> getAllProductInStockJdbc();
    Integer saveProductInStockJdbc(E instance);
}
