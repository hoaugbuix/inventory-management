package com.inventory.dev.controller;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInStockEntity;
import com.inventory.dev.service.ProductInStockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductInStockController {
    static final Logger log = Logger.getLogger(ProductInStockController.class);
    @Autowired
    private ProductInStockService productInStockService;

    @GetMapping(value = "/product-in-stock/list/{page}")
    public ResponseEntity<?> list(Model model, ProductInStockEntity productInStock, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductInStockEntity> productInStocks = productInStockService.getAll(productInStock, paging);
//        if (productInStocks.isEmpty()) {
//            throw new NotFoundException("Khong co san pham trong kho");
//        }
//        Map<Object, Integer> data = new HashMap<>();
//        if (page != 0 && productInStocks != null){
//            data.put(productInStocks, page);
//            log.info("data product in stock" + data.toString());
//        }
        return ResponseEntity.ok(productInStocks);
    }
}
