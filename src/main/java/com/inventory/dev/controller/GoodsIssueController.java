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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class GoodsIssueController {
    static final Logger log = Logger.getLogger(GoodsIssueController.class);
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

    @RequestMapping(value = {"/goods-issue/list", "/goods-issue/list/"})
    public String redirect() {
        return "redirect:/goods-issue/list/1";
    }

    @RequestMapping(value = "/goods-issue/list/{page}")
    public ResponseEntity<?> showInvoiceList(InvoiceEntity invoice, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        if (invoice == null) {
            invoice = new InvoiceEntity();
        }
        invoice.setType(Constant.TYPE_GOODS_ISSUES);
        List<InvoiceEntity> invoices = invoiceService.getList(invoice, paging);
        if (invoices.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/goods-issue/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id) throws Exception {
        log.info("Edit invoice with id=" + id);
        InvoiceEntity invoice = invoiceService.find("id", id).get(0);
        if (invoice != null) {
            invoiceService.update(invoice);
        }
        return ResponseEntity.ok("Edit success!");
    }

    @GetMapping("/goods-issue/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") int id) {
        log.info("View invoice with id=" + id);
        InvoiceEntity invoice = invoiceService.find("id", id).get(0);
        if (invoice == null) {
            throw new NotFoundException("Not found find by id" + id);
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/goods-issue/save")
    public ResponseEntity<?> save(@Valid @RequestBody InvoiceEntity invoice) {
        log.info("invoice find = " + invoice.toString());
        List<ProductInfoEntity> productInfos = productService.getAllProductInfo(null, null);

        for (ProductInfoEntity product : productInfos){
            invoice.setProductInfos(product);
        }
        invoice.setType(Constant.TYPE_GOODS_ISSUES);
        try {
            invoiceService.save(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

//    @GetMapping("/goods-issue/export")
//    public ModelAndView exportReport() {
//        ModelAndView modelAndView = new ModelAndView();
//        InvoiceEntity invoice = new InvoiceEntity();
//        invoice.setType(Constant.TYPE_GOODS_ISSUES);
//        List<InvoiceEntity> invoices = invoiceService.getList(invoice, null);
//        modelAndView.addObject(Constant.KEY_GOODS_RECEIPT_REPORT, invoices);
//        modelAndView.setView(new GoodsReceiptReport());
//        return modelAndView;
//    }


//    private Map<String, String> initMapProduct() {
//        List<ProductInfoEntity> productInfos = productService.getAllProductInfo(null, null);
//        Map<String, String> mapProduct = new HashMap<>();
//        for (ProductInfoEntity productInfo : productInfos) {
//            mapProduct.put(productInfo.getId().toString(), productInfo.getName());
//        }
//        return mapProduct;
//    }
}
