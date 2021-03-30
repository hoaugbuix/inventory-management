package com.inventory.dev.validate;


import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.service.UserService;
import com.inventory.dev.util.HashingPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class LoginValidator implements Validator {
    @Autowired
    private UserService userService;

    public boolean supports(Class<?> clazz) {
        return clazz == UserEntity.class;
    }

    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
            List<UserEntity> users = userService.findByProperty("username", user.getUsername());
            if (user != null && !users.isEmpty()) {
                if (!users.get(0).getPassword().equals(HashingPassword.encrypt(user.getPassword()))) {
                    errors.rejectValue("password", "msg.wrong.password");
                }
            } else {
                errors.rejectValue("username", "msg.wrong.username");
            }
        }
    }
}
