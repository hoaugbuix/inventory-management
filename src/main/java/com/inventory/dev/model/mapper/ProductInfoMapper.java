package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.model.dto.ProductInfoDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductInfoMapper implements RowMapper<ProductInfoEntity> {
    public static ProductInfoEntity toProductInfoEntity(ProductInfoDto dto) {
        ProductInfoEntity productInfo = new ProductInfoEntity();
        productInfo.setCode(dto.getCode());
        productInfo.setName(productInfo.getName());
        productInfo.setDescription(productInfo.getDescription());
        return productInfo;
    }

    @Override
    public ProductInfoEntity mapRow(ResultSet resultSet) {
        try {
            ProductInfoEntity productInfo = new ProductInfoEntity();
            productInfo.setId(resultSet.getInt("id"));
            return productInfo;
        }catch (SQLException e) {
            return null;
        }
    }
}
