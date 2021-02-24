package com.inventory.dev.controller;

import com.inventory.dev.entity.HistoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.service.HistoryService;
import com.inventory.dev.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HistoryController {
    static final Logger log = Logger.getLogger(HistoryController.class);
    @Autowired
    private HistoryService historyService;

    @GetMapping({"/history/list", "/history/list/"})
    public String redirect() {
        return "redirect:/history/list/1";
    }

    @RequestMapping(value = "/history/list/{page}")
    public ResponseEntity<?> list( HistoryEntity history, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<HistoryEntity> histories = historyService.getAll(history, paging);
        Map<String, String> mapType = new HashMap<>();
        mapType.put(String.valueOf(Constant.TYPE_ALL), "All");
        mapType.put(String.valueOf(Constant.TYPE_GOODS_RECEIPT), "Goods Receipt");
        mapType.put(String.valueOf(Constant.TYPE_GOODS_ISSUES), "Goods Issues");
//        model.addAttribute("histories", histories);
//        model.addAttribute("pageInfo", paging);
//        model.addAttribute("mapType", mapType);
        return ResponseEntity.ok(histories);
    }
}
