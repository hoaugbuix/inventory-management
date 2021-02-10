package com.inventory.dev.controller;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.model.dto.RoleDto;
import com.inventory.dev.model.request.CreateRoleReq;
import com.inventory.dev.service.RoleService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.RoleValidator;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RoleController {
    static final Logger log = Logger.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleValidator roleValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if (binder.getTarget().getClass() == RoleEntity.class) {
            binder.setValidator(roleValidator);
        }
    }

    @RequestMapping(value = {"/role/list", "/role/list/"})
    public String redirect() {
        return "redirect:/role/list/1";
    }

    @RequestMapping(value = "/role/list/{page}")
    public ResponseEntity<?> showRoleList(@Valid RoleEntity role, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<RoleEntity> roles = roleService.getRoleList(role, paging);
        return ResponseEntity.ok().body(roles);
    }

//    @GetMapping("/role/add")
//    public String add(Model model) {
//        model.addAttribute("titlePage", "Add Role");
//        model.addAttribute("modelForm", new RoleEntity());
//        model.addAttribute("viewOnly", false);
//        return "role-action";
//    }

    @GetMapping("/role/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id) throws Exception {
        log.info("Edit role with id=" + id);
        RoleEntity role = roleService.findByIdRole(id);
        if (role != null) {
            roleService.updateRole(role);
        }
        return ResponseEntity.ok().body("update thanh cong" + role);
    }

    @GetMapping("/role/view/{id}")
    public ResponseEntity<?> view(@Valid @PathVariable("id") int id) {
        log.info("View role with id=" + id);
        RoleEntity role = roleService.findByIdRole(id);
        List<Object> obj = new ArrayList<>();
        if (role != null) {
            obj.add(role);
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping("/role/save")
    public ResponseEntity<?> save(@RequestBody @Validated RoleEntity role,HttpSession session) {
        try {
            if(role.getId()!=null && role.getId()!=0) {
                try {
                    roleService.updateRole(role);
                    session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    session.setAttribute(Constant.MSG_ERROR, "Update has error");
                }
            }else {
                try {
                    roleService.saveRole(role);
                    session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
                }
            }
            return ResponseEntity.ok(role != null ? session.getAttribute(Constant.MSG_SUCCESS) : session.getAttribute(Constant.MSG_ERROR));

        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/role/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, HttpSession session) {
        log.info("Delete role with id=" + id);
        RoleEntity role = roleService.findByIdRole(id);
        if (role != null) {
            try {
                roleService.deleteRole(role);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return ResponseEntity.ok("Delete success!!!");
    }
}
