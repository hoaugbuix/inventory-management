package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.InvoiceDAO;
import com.inventory.dev.entity.InvoiceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<InvoiceEntity> implements InvoiceDAO<InvoiceEntity> {
}
