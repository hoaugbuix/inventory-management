package com.inventory.dev.controller;

import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.service.ProductService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.CategoryValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CategoryController {
    static final Logger log = Logger.getLogger(CategoryController.class);
    @Autowired
    private ProductService productService;
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
    public String showCategoryList(Model model, HttpSession session, @ModelAttribute("searchForm") CategoryEntity category, @PathVariable("page") int page) {
        Paging paging = new Paging(1);
        paging.setIndexPage(page);
        List<CategoryEntity> categories = productService.getAllCategory(category, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("categories", categories);
        return "category-list";

    }

    @GetMapping("/category/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Category");
        model.addAttribute("modelForm", new CategoryEntity());
        model.addAttribute("viewOnly", false);
        return "category-action";
    }

    @GetMapping("/category/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        log.info("Edit category with id=" + id);
        CategoryEntity category = productService.findByIdCategory(id);
        if (category != null) {
            model.addAttribute("titlePage", "Edit Category");
            model.addAttribute("modelForm", category);
            model.addAttribute("viewOnly", false);
            return "category-action";
        }
        return "redirect:/category/list";
    }

    @GetMapping("/category/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        log.info("View category with id=" + id);
        CategoryEntity category = productService.findByIdCategory(id);
        if (category != null) {
            model.addAttribute("titlePage", "View Category");
            model.addAttribute("modelForm", category);
            model.addAttribute("viewOnly", true);
            return "category-action";
        }
        return "redirect:/category/list";
    }

    @PostMapping("/category/save")
    public String save(Model model, @ModelAttribute("modelForm") @Validated CategoryEntity category, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (category.getId() != null) {
                model.addAttribute("titlePage", "Edit Category");
            } else {
                model.addAttribute("titlePage", "Add Category");
            }

            model.addAttribute("modelForm", category);
            model.addAttribute("viewOnly", false);
            return "category-action";

        }
        if (category.getId() != null && category.getId() != 0) {
            try {
                productService.updateCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        } else {
            try {
                productService.saveCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/category/list";

    }

    @GetMapping("/category/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        log.info("Delete category with id=" + id);
        CategoryEntity category = productService.findByIdCategory(id);
        if (category != null) {
            try {
                productService.deleteCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/category/list";
    }
}
