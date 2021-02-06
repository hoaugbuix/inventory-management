package com.inventory.dev.service;

import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceService {
    public void save(InvoiceEntity invoice) throws Exception;

    public void update(InvoiceEntity invoice) throws Exception;

    public List<InvoiceEntity> find(String property, Object value);

    public List<InvoiceEntity> getList(InvoiceEntity invoice, Paging paging);
}
