package com.inventory.dev.dao;

import java.util.List;

public interface HistoryDAO<E> extends BaseDAO<E> {
    List<E> findAllJdbc();

    Integer saveJdbc(E instance);
}
