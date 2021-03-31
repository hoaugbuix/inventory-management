package com.inventory.dev.service;

import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInStockEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductInStockService {
    List<ProductInStockEntity> getAll(ProductInStockEntity productInStock, Paging paging);

    void saveOrUpdate(InvoiceEntity invoice) throws Exception;
}
