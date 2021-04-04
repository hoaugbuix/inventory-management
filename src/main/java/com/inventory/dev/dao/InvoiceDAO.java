package com.inventory.dev.dao;

import java.util.List;

public interface InvoiceDAO<E> extends BaseDAO<E> {
    Integer saveInvoiceJdbc(E instance);
    void updateInvoiceJdbc(E instance);
    List<E> findAllInvoiceJdbc();
    E findOneInvoiceJdbc(int id);
}
