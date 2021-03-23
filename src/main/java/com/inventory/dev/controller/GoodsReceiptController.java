package com.inventory.dev.controller;

import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.exception.NotFoundException;
import com.inventory.dev.service.GoodsReceiptReport;
import com.inventory.dev.service.InvoiceService;
import com.inventory.dev.service.ProductService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.InvoiceValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsReceiptController {
    static final Logger log = Logger.getLogger(GoodsReceiptController.class);
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceValidator invoiceValidator;
    @Autowired
    private ProductService productService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if (binder.getTarget().getClass() == InvoiceEntity.class) {
            binder.setValidator(invoiceValidator);
        }
    }

    @RequestMapping(value = {"/goods-receipt/list", "/goods-receipt/list/"})
    public String redirect() {
        return "redirect:/goods-receipt/list/1";
    }

    @RequestMapping(value = "/goods-receipt/list/{page}")
    public ResponseEntity<?> showInvoiceList(Model model, HttpSession session, @ModelAttribute("searchForm") InvoiceEntity invoice, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        if (invoice == null) {
            invoice = new InvoiceEntity();
        }
        invoice.setType(Constant.TYPE_GOODS_RECEIPT);
        List<InvoiceEntity> invoices = invoiceService.getList(invoice, paging);
        if (invoices.isEmpty()) {
            throw new NotFoundException("Not found!");
        }
        return ResponseEntity.ok(invoices);
    }


    @GetMapping("/goods-receipt/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id) throws Exception {
        log.info("Edit invoice with id=" + id);
        InvoiceEntity invoice = invoiceService.find("id", id).get(0);
        if (invoice != null) {
           invoiceService.update(invoice);
        }
        return ResponseEntity.ok("Edit success!");
    }

    @GetMapping("/goods-receipt/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") int id) {
        log.info("View invoice with id=" + id);
        InvoiceEntity invoice = invoiceService.find("id", id).get(0);
        if (invoice == null) {
            throw new NotFoundException("Not found");
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/goods-receipt/save")
    public ResponseEntity<?> save( @Valid @RequestBody InvoiceEntity invoice) {
        invoice.setType(Constant.TYPE_GOODS_RECEIPT);
        if (invoice.getId() != null && invoice.getId() != 0) {
            try {
                invoiceService.update(invoice);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        } else {
            try {
                invoiceService.save(invoice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(invoice);

    }

    @GetMapping("/goods-receipt/export")
    public ModelAndView exportReport() {
        ModelAndView modelAndView = new ModelAndView();
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setType(Constant.TYPE_GOODS_RECEIPT);
        List<InvoiceEntity> invoices = invoiceService.getList(invoice, null);
        modelAndView.addObject(Constant.KEY_GOODS_RECEIPT_REPORT, invoices);
        modelAndView.setView(new GoodsReceiptReport());
        return modelAndView;
    }


    private Map<String, String> initMapProduct() {
        List<ProductInfoEntity> productInfos = productService.getAllProductInfo(null, null);
        Map<String, String> mapProduct = new HashMap<>();
        for (ProductInfoEntity productInfo : productInfos) {
            mapProduct.put(productInfo.getId().toString(), productInfo.getName());
        }
        return mapProduct;
    }
}
