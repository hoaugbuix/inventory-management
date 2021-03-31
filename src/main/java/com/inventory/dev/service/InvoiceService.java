package com.inventory.dev.service;

import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceService {
    void save(InvoiceEntity invoice) throws Exception;

    void update(InvoiceEntity invoice) throws Exception;

    List<InvoiceEntity> find(String property, Object value);

    List<InvoiceEntity> getList(InvoiceEntity invoice, Paging paging);
}
