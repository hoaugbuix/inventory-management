package com.inventory.dev.controller;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.service.CategoryService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.CategoryValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CategoryController {
    static final Logger log = Logger.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryValidator categoryValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if (binder.getTarget().getClass() == CategoryEntity.class) {
            binder.setValidator(categoryValidator);
        }
    }

    @RequestMapping(value = {"/category/list", "/category/list/"})
    public String redirect() {
        return "redirect:/category/list/1";
    }

    @RequestMapping(value = "/category/list/{page}")
    public ResponseEntity<?> showCategoryList(HttpSession session, CategoryEntity category, @PathVariable("page") int page) {
        Paging paging = new Paging(1);
        paging.setIndexPage(page);
        List<CategoryEntity> categories = categoryService.getAllCategory(category, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            session.removeAttribute(Constant.MSG_ERROR);
        }
        return ResponseEntity.ok(categories);

    }

    @GetMapping("/category/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Category");
        model.addAttribute("modelForm", new CategoryEntity());
        model.addAttribute("viewOnly", false);
        return "category-action";
    }

    @GetMapping("/category/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id) throws Exception {
        log.info("Edit category with id=" + id);
        CategoryEntity category = categoryService.findByIdCategory(id);
        if (category != null) {
            categoryService.updateCategory(category);
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category/view/{id}")
    public ResponseEntity<?> view(@Valid @PathVariable("id") int id) {
        log.info("View category with id=" + id);
        CategoryEntity category = categoryService.findByIdCategory(id);
        Set<Object> data = new HashSet<>();
        if (category != null) {
            data.add(category);
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("/category/save")
    public ResponseEntity<?> save(@RequestBody @Valid CategoryEntity category, HttpSession session) {
//        CategoryEntity cate = (CategoryEntity) categoryService.findCategory(category.getName(),category);
        if (category == null) {
            try {
                categoryService.updateCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }
        } else {
            try {
                categoryService.saveCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return ResponseEntity.ok(session.getAttribute(Constant.MSG_SUCCESS));

    }

    @GetMapping("/category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, HttpSession session) {
        log.info("Delete category with id=" + id);
        CategoryEntity category = categoryService.findByIdCategory(id);
        if (category != null) {
            try {
                categoryService.deleteCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return ResponseEntity.ok(session.getAttribute(Constant.MSG_SUCCESS));
    }
}
