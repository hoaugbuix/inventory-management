package com.inventory.dev.validate;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == RoleEntity.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleEntity role = (RoleEntity) target;
        ValidationUtils.rejectIfEmpty(errors, "roleName", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
    }
}
