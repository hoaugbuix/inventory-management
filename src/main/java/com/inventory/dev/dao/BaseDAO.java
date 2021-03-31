package com.inventory.dev.dao;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.model.mapper.RowMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDAO<E> {
    List<E> findAll(String queryStr, Map<String, Object> mapParams, Paging paging);

    E findById(Class<E> e, Serializable id);

    List<E> findByProperty(String property, Object value);

    void save(E instance);

    int insert(E instance);

    void update(E instance);

    //jdbc
    //Object... multiple parameter
    // resultSet is table --> RowMapper
    <E> List<E> queryJdbc(String sql, RowMapper<E> rowMapper, Object... parameters);

    void updateJdbc(String sql, Object... parameters);

    Integer insertJdbc(String sql, Object... parameters);

    Integer countJdbc(String sql, Object... parameters);

}
