package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.ProductInStockDAO;
import com.inventory.dev.entity.ProductInStockEntity;
import com.inventory.dev.model.mapper.ProductInStockMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInStockDAOImpl extends BaseDAOImpl<ProductInStockEntity> implements ProductInStockDAO<ProductInStockEntity> {
    @Override
    public List<ProductInStockEntity> getAllProductInStockJdbc() {
        String sql = "select * from product_in_stock";
        return queryJdbc(sql, new ProductInStockMapper());
    }

    @Override
    public Integer saveProductInStockJdbc(ProductInStockEntity productInStock) {
        StringBuilder sql = new StringBuilder("INSERT INTO product_in_stock");
        sql.append("(product_id, qty, price, active_flag, created_date, updated_date)");
        sql.append("VALUES (?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(), productInStock.getProductInfos(), productInStock.getQty(),
                productInStock.getPrice(), productInStock.getActiveFlag(), productInStock.getCreatedDate(),
                productInStock.getUpdatedDate());
    }
}
