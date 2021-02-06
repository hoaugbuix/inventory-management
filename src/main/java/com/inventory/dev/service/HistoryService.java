package com.inventory.dev.service;

import com.inventory.dev.entity.HistoryEntity;
import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    public List<HistoryEntity> getAll(HistoryEntity history, Paging paging);

    public void save(InvoiceEntity invoice, String action);
}
