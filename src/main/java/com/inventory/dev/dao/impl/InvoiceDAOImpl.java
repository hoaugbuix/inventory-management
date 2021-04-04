package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.InvoiceDAO;
import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.model.mapper.InvoiceMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<InvoiceEntity> implements InvoiceDAO<InvoiceEntity> {
    @Override
    public Integer saveInvoiceJdbc(InvoiceEntity invoice) {
        StringBuilder sql = new StringBuilder("INSERT INTO invoice (code, type, product_id, qty, price, to_date, from_date, active_flag, created_date, updated_date)");
        sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(), invoice.getCode(), invoice.getType(), invoice.getProductInfos(), invoice.getQty(),
                invoice.getPrice(), invoice.getToDate(), invoice.getFromDate(),
                invoice.getCreatedDate(), invoice.getUpdatedDate());
    }

    @Override
    public void updateInvoiceJdbc(InvoiceEntity invoice) {
        StringBuilder sql = new StringBuilder("UPDATE invoice SET code = ?, type = ?, product_id = ?, qty = ?, price = ?, to_date = ?, from_date = ?, active_flag = ?, created_date = ?, updated_date = ?)");
        updateJdbc(sql.toString(), invoice.getCode(), invoice.getType(), invoice.getProductInfos(), invoice.getQty(),
                invoice.getPrice(), invoice.getToDate(), invoice.getFromDate(),
                invoice.getCreatedDate(), invoice.getUpdatedDate());
    }

    @Override
    public List<InvoiceEntity> findAllInvoiceJdbc() {
        String sql = "select * from invoice";
        return queryJdbc(sql, new InvoiceMapper());
    }

    @Override
    public InvoiceEntity findOneInvoiceJdbc(int id) {
        String sql = "Select * from invoice where id = ?";
        List<InvoiceEntity> invoice = queryJdbc(sql, new InvoiceMapper(), id);
        return invoice.isEmpty() ? null : invoice.get(0);
    }
}
