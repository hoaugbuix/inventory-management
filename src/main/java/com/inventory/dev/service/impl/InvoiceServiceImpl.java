package com.inventory.dev.service.impl;

import com.inventory.dev.dao.InvoiceDAO;
import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.service.HistoryService;
import com.inventory.dev.service.InvoiceService;
import com.inventory.dev.service.ProductInStockService;
import com.inventory.dev.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceServiceImpl implements InvoiceService {
    static final Logger log = Logger.getLogger(InvoiceServiceImpl.class);
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProductInStockService productInStockService;
    @Autowired
    private InvoiceDAO<InvoiceEntity> invoiceDAO;

    @Override
    public void save(InvoiceEntity invoice) throws Exception {
        ProductInfoEntity productInfo = new ProductInfoEntity();
//        productInfo.setId(invoice.getProductId());
        invoice.setProductInfos(productInfo);
        invoice.setActiveFlag(1);
        invoice.setCreatedDate(new Date());
        invoice.setUpdatedDate(new Date());
        invoiceDAO.save(invoice);
        historyService.save(invoice, Constant.ACTION_ADD);

        productInStockService.saveOrUpdate(invoice);
    }

    @Override
    public void update(InvoiceEntity invoice) throws Exception {
        int originQty = invoiceDAO.findById(InvoiceEntity.class, invoice.getId()).getQty();
        ProductInfoEntity productInfo = new ProductInfoEntity();
//        productInfo.setId(invoice.getProductId());
        invoice.setProductInfos(productInfo);
        invoice.setUpdatedDate(new Date());
        InvoiceEntity invoice2 = new InvoiceEntity();
        invoice2.setProductInfos(invoice.getProductInfos());
        invoice2.setQty(invoice.getQty() - originQty);
        invoice2.setPrice(invoice.getPrice());
        invoiceDAO.update(invoice);
        historyService.save(invoice, Constant.ACTION_EDIT);
        productInStockService.saveOrUpdate(invoice2);
    }

    @Override
    public List<InvoiceEntity> find(String property, Object value) {
        return invoiceDAO.findByProperty(property, value);
    }

    @Override
    public List<InvoiceEntity> getList(InvoiceEntity invoice, Paging paging) {
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if (invoice != null) {
            if (invoice.getType() != 0) {
                queryStr.append(" and model.type=:type");
                mapParams.put("type", invoice.getType());
            }
            if (!StringUtils.isEmpty(invoice.getCode())) {
                queryStr.append(" and model.code =:code ");
                mapParams.put("code", invoice.getCode());
            }
            if (invoice.getFromDate() != null) {
                queryStr.append(" and model.updateDate >= :fromDate");
                mapParams.put("fromDate", invoice.getFromDate());
            }
            if (invoice.getToDate() != null) {
                queryStr.append(" and model.updateDate <= :toDate");
                mapParams.put("toDate", invoice.getToDate());
            }
        }
        return invoiceDAO.findAll(queryStr.toString(), mapParams, paging);
    }
}
