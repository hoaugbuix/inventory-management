package com.inventory.dev.validate;

import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ProductInfoValidator implements Validator {
    @Autowired
    private ProductService productService;

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz == ProductInfoEntity.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductInfoEntity productInfo = (ProductInfoEntity) target;
        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        if (productInfo.getId() != null) {
            ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");
        }
        if (productInfo.getCode() != null) {
            List<ProductInfoEntity> results = productService.findProductInfo("code", productInfo.getCode());
            if (results != null && !results.isEmpty()) {
                if (productInfo.getId() != null && productInfo.getId() != 0) {
                    if (results.get(0).getId() != productInfo.getId()) {
                        errors.rejectValue("code", "msg.code.exist");
                    }
                } else {
                    errors.rejectValue("code", "msg.code.exist");
                }

            }
        }
//        if (!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
//            String extension = FilenameUtils.getExtension(productInfo.getMultipartFile().getOriginalFilename());
//            if (!extension.equals("jpg") && !extension.equals("png")) {
//                errors.rejectValue("multipartFile", "msg.file.extension.error");
//            }
//        }

    }
}
