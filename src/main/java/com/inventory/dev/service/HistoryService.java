package com.inventory.dev.service;

import com.inventory.dev.entity.HistoryEntity;
import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    List<HistoryEntity> getAll(HistoryEntity history, Paging paging);

    void save(InvoiceEntity invoice, String action);
}
