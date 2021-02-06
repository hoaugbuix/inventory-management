package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.ProductInfoDAO;
import com.inventory.dev.entity.ProductInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoDAOImpl extends BaseDAOImpl<ProductInfoEntity> implements ProductInfoDAO<ProductInfoEntity> {
}
