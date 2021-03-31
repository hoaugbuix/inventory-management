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
    public InvoiceEntity saveJdbc(InvoiceEntity instance) {
        return null;
    }

    @Override
    public InvoiceEntity updateJdbc(InvoiceEntity instance) {
        return null;
    }

    @Override
    public List<InvoiceEntity> findAll() {
        String sql = "select * from invoice";
        return queryJdbc(sql, new InvoiceMapper());
    }

    @Override
    public InvoiceEntity findOne(int id) {
        String sql = "Select * from invoice where id = ?";
        List<InvoiceEntity> invoice = queryJdbc(sql, new InvoiceMapper(), id);
        return null;
    }
}
