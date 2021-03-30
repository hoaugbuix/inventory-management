package com.inventory.dev.controller;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.exception.NotFoundException;
import com.inventory.dev.model.request.CreateProductInfoReq;
import com.inventory.dev.model.request.UpdateProductInfoReq;
import com.inventory.dev.service.CategoryService;
import com.inventory.dev.service.ProductService;
import com.inventory.dev.validate.ProductInfoValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProductInfoController {
    static final Logger log = Logger.getLogger(ProductInfoController.class);
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductInfoValidator productInfoValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if (binder.getTarget().getClass() == ProductInfoEntity.class) {
            binder.setValidator(productInfoValidator);
        }
    }

    @RequestMapping(value = {"/product-info/list", "/product-info/list/"})
    public String redirect() {
        return "redirect:/product-info/list/1";
    }

    @RequestMapping(value = "/product-info/list/{page}")
    public ResponseEntity<?> showProductInfoList(ProductInfoEntity productInfo, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductInfoEntity> products = productService.getAllProductInfo(productInfo, paging);
        return ResponseEntity.ok(products);
    }


    @PutMapping("/product-info/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id, @Valid @RequestBody UpdateProductInfoReq req) throws Exception {
        log.info("Edit productInfo with id=" + id);
        ProductInfoEntity productInfo = productService.findByIdProductInfo(id);

        List<CategoryEntity> categories = categoryService.getAllCategory(null, null);
        Map<String, String> mapCategory = new HashMap<>();
        for (CategoryEntity category : categories) {
            mapCategory.put(String.valueOf(category.getId()), category.getName());
            if (category.getId() == req.getCateId()){
                productInfo.setCategories(category);
            }
        }
        if (productInfo != null) {
            try {
                productInfo.setName(req.getName());
                productInfo.setCode(req.getCode());
                productInfo.setDescription(req.getDescription());
                productInfo.setDescription(req.getDescription());
                productInfo.setActiveFlag(req.getActiveFlag());
                productInfo.setImgUrl(req.getImgUrl());
                productInfo.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                productService.updateProductInfo(productInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Update success");
    }

    @GetMapping("/product-info/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") int id) {
        log.info("View productInfo with id=" + id);
        ProductInfoEntity productInfo = productService.findByIdProductInfo(id);
        if (productInfo != null) {
            throw new NotFoundException("Not found");
        }
        return ResponseEntity.ok(productInfo);
    }

    @PostMapping("/product-info/save")
    public ResponseEntity<?> save(@Valid @RequestBody ProductInfoEntity productInfo) {

        try {
            List<CategoryEntity> category =  categoryService.getAllCategory(null, null);
//            CategoryEntity ca = categoryService.findByIdCategory();
//          category.setId(productInfo.getCateId());
            for (CategoryEntity cate : category) {
                productInfo.setCategories(cate);
            }
            productService.saveProductInfo(productInfo);
        } catch (Exception e) {
                e.printStackTrace();
                ResponseEntity.badRequest().body(e.getMessage());
        }
        return new ResponseEntity<>(productInfo, HttpStatus.OK);
    }

    @GetMapping("/product-info/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        log.info("Delete productInfo with id=" + id);
        ProductInfoEntity productInfo = productService.findByIdProductInfo(id);
        if (productInfo != null) {
            try {
                productService.deleteProductInfo(productInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("Delete success!");
    }
}
