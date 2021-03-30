package com.inventory.dev.controller;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.service.RoleService;
import com.inventory.dev.validate.RoleValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/role/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id, @Valid @RequestBody RoleEntity roleEntity) throws Exception {
        log.info("Edit role with id=" + id);
        RoleEntity role = roleService.findByIdRole(id);
        if (role != null) {
            try {
                roleService.updateRole(roleEntity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().body("update thanh cong" + roleEntity.toString());
    }

    @GetMapping("/role/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") int id) {
        log.info("View role with id=" + id);
        RoleEntity role = roleService.findByIdRole(id);
        List<Object> obj = new ArrayList<>();
        if (role != null) {
            obj.add(role);
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping("/role/save")
    public ResponseEntity<?> save(@Valid @RequestBody RoleEntity role) {
        if (role.getId() != null && role.getId() != 0) {
            try {
                roleService.updateRole(role);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        } else {
            try {
                roleService.saveRole(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping("/role/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        log.info("Delete role with id=" + id);
        RoleEntity role = roleService.findByIdRole(id);
        if (role != null) {
            try {
                roleService.deleteRole(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("Delete success!!!");
    }
}
