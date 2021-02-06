package com.inventory.dev.validate;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;


@Component
public class CategoryValidator implements Validator {
    @Autowired
    private ProductService productService;

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz == CategoryEntity.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryEntity category = (CategoryEntity) target;
        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        if (category.getCode() != null) {
            List<CategoryEntity> results = productService.findCategory("code", category.getCode());
            if (results != null && !results.isEmpty()) {
                if (category.getId() != null && category.getId() != 0) {
                    if (results.get(0).getId() != category.getId()) {
                        errors.rejectValue("code", "msg.code.exist");
                    }
                } else {
                    errors.rejectValue("code", "msg.code.exist");
                }
            }
        }
    }
}
