package com.inventory.dev.service;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void saveCategory(CategoryEntity category) throws Exception;

    void updateCategory(CategoryEntity category) throws Exception;

    void deleteCategory(CategoryEntity category) throws Exception;

    List<CategoryEntity> findCategory(String property, Object value);

    List<CategoryEntity> getAllCategory(CategoryEntity category, Paging paging);

    CategoryEntity findByIdCategory(int id);

    // Jdbc
    List<CategoryEntity> findAll();
    CategoryEntity findById(int id);
    CategoryEntity findByCode(String code);
    CategoryEntity save(CategoryEntity category);
    CategoryEntity update(CategoryEntity category);
    void delete(int[] ids);
}
