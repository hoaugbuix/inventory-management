package com.inventory.dev.dao;

import java.util.List;

public interface InvoiceDAO<E> extends BaseDAO<E> {
    E saveJdbc(E instance);
    E updateJdbc(E instance);
    List<E> findAll();
    E findOne(int id);
}
