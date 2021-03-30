package com.inventory.dev.controller;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.exception.NotFoundException;
import com.inventory.dev.service.CategoryService;
import com.inventory.dev.validate.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<?> showCategoryList(CategoryEntity category, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<CategoryEntity> categories = categoryService.getAllCategory(category, paging);
        if (categories == null) {
            throw new NotFoundException("Not Found");
        }
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/category/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id, @Valid @RequestBody CategoryEntity cateDetail) throws Exception {
        log.info("Edit category with id=" + id);
        CategoryEntity category = categoryService.findByIdCategory(id);
        if (category == null) {
            throw new NotFoundException("Not found id categories");
        }
        try {
            category.setName(cateDetail.getName());
            category.setCode(cateDetail.getCode());
            category.setDescription(cateDetail.getDescription());
            category.setActiveFlag(cateDetail.getActiveFlag());
            category.setUpdatedDate(new Date());
            categoryService.updateCategory(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    public ResponseEntity<?> save(@RequestBody @Valid CategoryEntity category) {
//        CategoryEntity cate = (CategoryEntity) categoryService.findCategory(category.getName(),category);
        try {
            categoryService.saveCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        log.info("Delete category with id=" + id);
        CategoryEntity category = categoryService.findByIdCategory(id);
        if (category != null) {
            try {
                categoryService.deleteCategory(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("Delete success");
    }
}
