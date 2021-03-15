package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.model.dto.ProductInfoDto;

public class ProductInfoMapper {
    public static ProductInfoEntity toProductInfoEntity(ProductInfoDto dto){
        ProductInfoEntity productInfo = new ProductInfoEntity();
        productInfo.setCode(dto.getCode());
        productInfo.setName(productInfo.getName());
        productInfo.setDescription(productInfo.getDescription());
        return  productInfo;
    }
}
