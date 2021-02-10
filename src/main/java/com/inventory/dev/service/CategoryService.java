package com.inventory.dev.service;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public void saveCategory(CategoryEntity category) throws Exception;

    public void updateCategory(CategoryEntity category) throws Exception;

    public void deleteCategory(CategoryEntity category) throws Exception;

    public List<CategoryEntity> findCategory(String property, Object value);

    public List<CategoryEntity> getAllCategory(CategoryEntity category, Paging paging);

    public CategoryEntity findByIdCategory(int id);
}
