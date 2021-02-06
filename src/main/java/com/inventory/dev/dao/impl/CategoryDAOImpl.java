package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.CategoryDAO;
import com.inventory.dev.entity.CategoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl extends BaseDAOImpl<CategoryEntity> implements CategoryDAO<CategoryEntity> {
}
