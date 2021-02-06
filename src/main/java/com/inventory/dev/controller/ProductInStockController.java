package com.inventory.dev.controller;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInStockEntity;
import com.inventory.dev.service.ProductInStockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductInStockController {
    static final Logger log = Logger.getLogger(ProductInStockController.class);
    @Autowired
    private ProductInStockService productInStockService;

    @GetMapping({"/product-in-stock/list", "/product-in-stock/list/"})
    public String redirect() {
        return "redirect:/product-in-stock/list/1";
    }

    @RequestMapping(value = "/product-in-stock/list/{page}")
    public String list(Model model, @ModelAttribute("searchForm") ProductInStockEntity productInStock, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductInStockEntity> productInStocks = productInStockService.getAll(productInStock, paging);
        model.addAttribute("products", productInStocks);
        model.addAttribute("pageInfo", paging);
        return "product-in-stock";
    }
}
