package com.inventory.dev.service.impl;

import com.inventory.dev.dao.CategoryDAO;
import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = Logger.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryDAO<CategoryEntity> categoryDAO;

    @Override
    public void saveCategory(CategoryEntity category) throws Exception {
        log.info("Insert category " + category.toString());
        category.setActiveFlag(1);
        category.setCreatedDate(new Date());
        category.setUpdatedDate(new Date());
        categoryDAO.save(category);
    }

    @Override
    public void updateCategory(CategoryEntity category) throws Exception {
        log.info("Update category " + category.toString());
        category.setUpdatedDate(new Date());

        categoryDAO.update(category);
    }

    @Override
    public void deleteCategory(CategoryEntity category) throws Exception {
        category.setActiveFlag(0);
        category.setUpdatedDate(new Date());
        log.info("Delete category " + category.toString());
        categoryDAO.update(category);
    }

    @Override
    public List<CategoryEntity> findCategory(String property, Object value) {
        log.info("=====Find by property category start====");
        log.info("property =" + property + " value=" + value.toString());
        return categoryDAO.findByProperty(property, value);
    }

    @Override
    public List<CategoryEntity> getAllCategory(CategoryEntity category, Paging paging) {
        log.info("show all category");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if (category != null) {
            if (category.getId() != null && category.getId() != 0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", category.getId());
            }
            if (category.getCode() != null && !StringUtils.isEmpty(category.getCode())) {
                queryStr.append(" and model.code=:code");
                mapParams.put("code", category.getCode());
            }
            if (category.getName() != null && !StringUtils.isEmpty(category.getName())) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%" + category.getName() + "%");
            }
        }
        return categoryDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    @Override
    public CategoryEntity findByIdCategory(int id) {
        log.info("find category by id =" + id);
        return categoryDAO.findById(CategoryEntity.class, id);
    }
}
