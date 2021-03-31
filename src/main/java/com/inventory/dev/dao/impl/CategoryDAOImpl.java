package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.CategoryDAO;
import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.model.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl extends BaseDAOImpl<CategoryEntity> implements CategoryDAO<CategoryEntity> {

    @Override
    public List<CategoryEntity> findAll() {
        String sql = "select * from category";
        return queryJdbc(sql, new CategoryMapper());
    }

    @Override
    public CategoryEntity findOne(int id) {
        String sql = "select * from category where id = ?";
        List<CategoryEntity> category = queryJdbc(sql, new CategoryMapper(), id);
        return category.isEmpty() ? null : category.get(0);
    }

    @Override
    public CategoryEntity findByCode(String code) {
        String sql = "select * from category where code = ?";
        List<CategoryEntity> category = queryJdbc(sql, new CategoryMapper(), code);
        return category.isEmpty() ? null : category.get(0);
    }

    @Override
    public Integer saveCategory(CategoryEntity category) {
        StringBuilder sql = new StringBuilder("INSERT INTO category (name, code, description, active_flag, created_date, updated_date)");
        sql.append(" VALUES(?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(),category.getName(), category.getCode(), category.getDescription(),
                category.getActiveFlag(), category.getCreatedDate(), category.getUpdatedDate());
    }

    @Override
    public void updateCategory(CategoryEntity category) {
        StringBuilder sql = new StringBuilder("UPDATE category SET name = ?,code = ?, description = ?, active_flag = ?, created_date = ?, updated_date = ?");
        updateJdbc(sql.toString(), category.getName(), category.getCode(), category.getDescription(), category.getActiveFlag(),category.getCreatedDate(), category.getUpdatedDate());
    }

    @Override
    public void deleteCategory(int id) {
        String sql = "UPDATE category SET active_flag = 0, updated_date = now() WHERE id = ?";
        updateJdbc(sql,id);
    }
}
