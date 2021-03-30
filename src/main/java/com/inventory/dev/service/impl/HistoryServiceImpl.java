package com.inventory.dev.service.impl;

import com.inventory.dev.dao.HistoryDAO;
import com.inventory.dev.entity.HistoryEntity;
import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.service.HistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HistoryServiceImpl implements HistoryService {
    private static final Logger log = Logger.getLogger(HistoryServiceImpl.class);
    @Autowired
    private HistoryDAO<HistoryEntity> historyDAO;

    @Override
    public List<HistoryEntity> getAll(HistoryEntity history, Paging paging) {
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if (history != null) {
            if (history.getProductInfo() != null) {
                if (!StringUtils.isEmpty(history.getProductInfo().getCategories().getName())) {
                    queryStr.append(" and model.productInfo.category.name like :cateName");
                    mapParams.put("cateName", "%" + history.getProductInfo().getCategories().getName() + "%");
                }
                if (!StringUtils.isEmpty(history.getProductInfo().getCode())) {
                    queryStr.append(" and model.productInfo.code=:code");
                    mapParams.put("code", history.getProductInfo().getCode());
                }
                if (!StringUtils.isEmpty(history.getProductInfo().getName())) {
                    queryStr.append(" and model.productInfo.name like :name");
                    mapParams.put("name", "%" + history.getProductInfo().getName() + "%");
                }
            }
            if (!StringUtils.isEmpty(history.getActionName())) {
                queryStr.append(" and model.actionName like :actionName");
                mapParams.put("actionName", "%" + history.getActionName() + "%");
            }
            if (history.getType() != 0) {
                queryStr.append(" and model.type = :type");
                mapParams.put("type", history.getType());
            }
        }
        return historyDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    @Override
    public void save(InvoiceEntity invoice, String action) {
        log.info("action" + action);
        HistoryEntity history = new HistoryEntity();
        history.setProductInfo(invoice.getProductInfos());
        history.setQty(invoice.getQty());
        history.setType(invoice.getType());
        history.setPrice(invoice.getPrice());
        history.setActiveFlag(1);
        history.setActionName(action);
        history.setCreatedDate(new Date());
        history.setUpdatedDate(new Date());
        historyDAO.save(history);
    }
}
