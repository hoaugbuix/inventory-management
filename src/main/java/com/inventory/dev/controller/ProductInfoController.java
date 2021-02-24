package com.inventory.dev.controller;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.exception.NotFoundException;
import com.inventory.dev.service.CategoryService;
import com.inventory.dev.service.ProductService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.ProductInfoValidator;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> showProductInfoList(HttpSession session, @ModelAttribute("searchForm") ProductInfoEntity productInfo, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductInfoEntity> products = productService.getAllProductInfo(productInfo, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            session.removeAttribute(Constant.MSG_ERROR);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product-info/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add ProductInfo");
        model.addAttribute("modelForm", new ProductInfoEntity());
        List<CategoryEntity> categories = categoryService.getAllCategory(null, null);
        Map<String, String> mapCategory = new HashMap<>();
        for (CategoryEntity category : categories) {
            mapCategory.put(String.valueOf(category.getId()), category.getName());
        }
        model.addAttribute("mapCategory", mapCategory);
        model.addAttribute("mapCategory", mapCategory);
        model.addAttribute("viewOnly", false);
        return "productInfo-action";
    }

    @GetMapping("/product-info/edit/{id}")
    public ResponseEntity<?> edit( @PathVariable("id") int id) {
        log.info("Edit productInfo with id=" + id);
        ProductInfoEntity productInfo = productService.findByIdProductInfo(id);
        if (productInfo != null) {
            List<CategoryEntity> categories = categoryService.getAllCategory(null, null);
            Map<String, String> mapCategory = new HashMap<>();
            for (CategoryEntity category : categories) {
                mapCategory.put(String.valueOf(category.getId()), category.getName());
            }
            productInfo.setCategories(productInfo.getCategories());
        }
        return ResponseEntity.ok(productInfo);
    }

    @GetMapping("/product-info/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") int id) {
        log.info("View productInfo with id=" + id);
        ProductInfoEntity productInfo = productService.findByIdProductInfo(id);
        if (productInfo != null) {
            throw new NotFoundException("khong tim thay");
        }
        return ResponseEntity.ok(productInfo);
    }

    @PostMapping("/product-info/save")
    public ResponseEntity<?> save(@RequestBody @Valid ProductInfoEntity productInfo, HttpSession session) {
        if (productInfo.getId() != null) {
            List<CategoryEntity> categories = categoryService.getAllCategory(null, null);
            Map<String, String> mapCategory = new HashMap<>();
            for (CategoryEntity category : categories) {
                mapCategory.put(String.valueOf(category.getId()), category.getName());
            }
        }
        CategoryEntity category = new CategoryEntity();
        productInfo.setCategories(category);
        if (productInfo.getId() != null && productInfo.getId() != 0) {
            try {
                productService.updateProductInfo(productInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }
        } else {
            try {
                productService.saveProductInfo(productInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return ResponseEntity.ok(session.getAttribute(Constant.MSG_SUCCESS));
    }

    @GetMapping("/product-info/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, HttpSession session) {
        try {
            log.info("Delete productInfo with id=" + id);
            ProductInfoEntity productInfo = productService.findByIdProductInfo(id);
            if (productInfo != null) {
                try {
                    productService.deleteProductInfo(productInfo);
                    session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
                }
            }
            return ResponseEntity.ok(session.getAttribute(Constant.MSG_SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.ok(session.getAttribute(Constant.MSG_ERROR));
        }
    }
}
