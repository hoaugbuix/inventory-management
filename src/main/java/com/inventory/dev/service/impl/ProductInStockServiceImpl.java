package com.inventory.dev.service.impl;

import com.inventory.dev.dao.ProductInStockDAO;
import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInStockEntity;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.service.ProductInStockService;
import com.inventory.dev.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductInStockServiceImpl implements ProductInStockService {
    private static final Logger log = Logger.getLogger(ProductInStockServiceImpl.class);
    @Autowired
    private ProductInStockDAO<ProductInStockEntity> productInStockDAO;

    @Override
    public List<ProductInStockEntity> getAll(ProductInStockEntity productInStock, Paging paging) {
        log.info("show all productInStock");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productInStock!=null && productInStock.getProductInfos()!=null) {
            if(!StringUtils.isEmpty(productInStock.getProductInfos().getCategories().getName())) {
                queryStr.append(" and model.productInfo.category.name like :cateName");
                mapParams.put("cateName","%"+productInStock.getProductInfos().getCategories().getName()+"%");
            }
            if(!StringUtils.isEmpty(productInStock.getProductInfos().getCode())) {
                queryStr.append(" and model.productInfo.code=:code");
                mapParams.put("code", productInStock.getProductInfos().getCode());
            }
            if( !StringUtils.isEmpty(productInStock.getProductInfos().getName()) ) {
                queryStr.append(" and model.productInfo.name like :name");
                mapParams.put("name", "%"+productInStock.getProductInfos().getName()+"%");
            }
        }
        return productInStockDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    @Override
    public void saveOrUpdate(InvoiceEntity invoice) throws Exception {
        log.info("product in stock ");
        if (invoice.getProductInfos() != null) {
            int id = invoice.getProductInfos().getId();
            List<ProductInStockEntity> products = productInStockDAO.findByProperty("productInfos.id", id);
            ProductInStockEntity product = null;
            if (products != null && !products.isEmpty()) {
                product = products.get(0);
                log.info("update qty=" + invoice.getQty() + " and price=" + invoice.getPrice());
                if (invoice.getType() == Constant.TYPE_GOODS_ISSUES) {
                    product.setQty(product.getQty() - invoice.getQty());
                } else {
                    product.setQty(product.getQty() + invoice.getQty());
                }

                // type =1 receipt , type =2 issues
                if (invoice.getType() == Constant.TYPE_GOODS_RECEIPT) {
                    product.setPrice(invoice.getPrice());
                }
                product.setUpdatedDate(new Date());
                productInStockDAO.update(product);

            } else if (invoice.getType() == Constant.TYPE_GOODS_RECEIPT) {
                log.info("insert to stock qty=" + invoice.getQty() + " and price=" + invoice.getPrice());
                product = new ProductInStockEntity();
                ProductInfoEntity productInfo = new ProductInfoEntity();
                productInfo.setId(invoice.getProductInfos().getId());
                product.setProductInfos(productInfo);
                product.setActiveFlag(1);
                product.setCreatedDate(new Date());
                product.setUpdatedDate(new Date());
                product.setQty(invoice.getQty());
                product.setPrice(invoice.getPrice());
                productInStockDAO.save(product);
            }
        }
    }
}
