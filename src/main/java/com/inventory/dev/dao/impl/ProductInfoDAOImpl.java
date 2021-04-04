package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.ProductInfoDAO;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.model.mapper.ProductInfoMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoDAOImpl extends BaseDAOImpl<ProductInfoEntity> implements ProductInfoDAO<ProductInfoEntity> {
    @Override
    public Integer saveProductInfoJdbc(ProductInfoEntity productInfo) {
        StringBuilder sql = new StringBuilder("INSERT INTO product_info (code, name, img_url, description, active_flag, created_date, updated_date, cate_id)");
        sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(), productInfo.getCode(), productInfo.getName(),
                productInfo.getImgUrl(), productInfo.getActiveFlag(), productInfo.getCreatedDate(),
                productInfo.getUpdatedDate(), productInfo.getCategories());
    }

    @Override
    public void updateProductInfoJdbc(ProductInfoEntity productInfo) {
        StringBuilder sql = new StringBuilder("UPDATE category SET code = ?,name = ?, img_url = ?, description = ?, active_flag = ?, created_date = ?, updated_date = ?, cate_id = ?");
        updateJdbc(sql.toString(), productInfo.getCode(), productInfo.getName(),
                productInfo.getImgUrl(), productInfo.getActiveFlag(), productInfo.getCreatedDate(),
                productInfo.getUpdatedDate(), productInfo.getCategories());
    }

    @Override
    public void deleteProductInfoJdbc(int id) {
        String sql = "UPDATE product_info SET active_flag = 0, updated_date = now() WHERE id = ?";
        updateJdbc(sql,id);
    }

    @Override
    public ProductInfoEntity findProductInfoByIdJdbc(int id) {
        String sql = "select * from product_info where id = ?";
        List<ProductInfoEntity> productInfo = queryJdbc(sql, new ProductInfoMapper(), id);
        return productInfo.isEmpty() ? null : productInfo.get(0);
    }

    @Override
    public List<ProductInfoEntity> findAllProductInfoJdbc() {
        String sql = "select * from product_info";
        return queryJdbc(sql, new ProductInfoMapper());
    }
}
