package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.ProductInStockDAO;
import com.inventory.dev.entity.ProductInStockEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInStockDAOImpl extends BaseDAOImpl<ProductInStockEntity> implements ProductInStockDAO<ProductInStockEntity> {
}
