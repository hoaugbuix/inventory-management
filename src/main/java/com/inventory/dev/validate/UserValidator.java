package com.inventory.dev.validate;

import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;


@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserEntity.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;
        ValidationUtils.rejectIfEmpty(errors, "username", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
        if (user.getId() == null) {
            ValidationUtils.rejectIfEmpty(errors, "firstName", "msg.required");
        }
        List<UserEntity> users = userService.findByProperty("username", user.getUsername());
        if (users != null && !users.isEmpty()) {
            errors.rejectValue("username", "msg.username.exist");
        }
    }
}
