package com.inventory.dev.controller;

import com.inventory.dev.entity.*;
import com.inventory.dev.service.MenuService;
import com.inventory.dev.service.RoleService;
import com.inventory.dev.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = {"/menu/list", "/menu/list/"})
    public String redirect() {
        return "redirect:/menu/list/1";
    }

    @RequestMapping("/menu/list/{page}")
    public ResponseEntity<?> menuList(@PathVariable("page") int page, MenuEntity menu, HttpSession session) {
        Paging paging = new Paging(15);
        paging.setIndexPage(page);
        List<MenuEntity> menuList = menuService.getListMenu(paging, menu);
        List<RoleEntity> roles = roleService.getRoleList(null, null);
        Collections.sort(roles, (o1, o2) -> o1.getId() - o2.getId());
        for (MenuEntity item : menuList) {
            Map<Integer, Integer> mapAuth = new TreeMap<>();
            for (RoleEntity role : roles) {
                mapAuth.put(role.getId(), 0);// 1-0 ,2-0,3-0
            }
//            for (Object obj : item.getAuths()) {
//                AuthEntity auth = (AuthEntity) obj;
//                mapAuth.put(auth.getRoles().getId(), auth.getPermission());
//            }
//            item.setMapAuth(mapAuth);
        }
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            session.removeAttribute(Constant.MSG_ERROR);
        }
//        model.addAttribute("menuList", menuList);
//        model.addAttribute("roles", roles);
//        model.addAttribute("pageInfo", paging);
        return ResponseEntity.ok(menuList);
    }

    @GetMapping("/menu/change-status/{id}")
    public ResponseEntity<?> change(Model model, @PathVariable("id") int id, HttpSession session) {
        try {
            menuService.changeStatus(id);
            session.setAttribute(Constant.MSG_SUCCESS, "Change status success!!!");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute(Constant.MSG_ERROR, "Change status has error!!!");
        }
        return ResponseEntity.ok(session.equals(Constant.MSG_SUCCESS) ? Constant.MSG_SUCCESS : Constant.MSG_ERROR);
    }

    @GetMapping("/menu/permission")
    public String permission(Model model) {
        model.addAttribute("modelForm", new AuthFormEntity());
        initSelectbox(model);
        return "menu-permission";
    }

    @PostMapping("/menu/update-permission")
    public ResponseEntity<?> updatePermission(Model model, HttpSession session, @ModelAttribute("modelForm") AuthFormEntity authForm) {
        try {
            menuService.updatePermission(authForm.getRoleId(), authForm.getMenuId(), authForm.getPermission());
            session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute(Constant.MSG_ERROR, "Update has error!!!");
        }
        return ResponseEntity.ok(Constant.MSG_SUCCESS);
    }

    private void initSelectbox(Model model) {
        List<RoleEntity> roles = roleService.getRoleList(null, null);
        List<MenuEntity> menus = menuService.getListMenu(null, null);
        Map<Integer, String> mapRole = new HashMap<>();
        Map<Integer, String> mapMenu = new HashMap<>();
        for (RoleEntity role : roles) {
            mapRole.put(role.getId(), role.getRoleName());
        }
        for (MenuEntity menu : menus) {
            mapMenu.put(menu.getId(), menu.getUrl());
        }
        model.addAttribute("mapRole", mapRole);
        model.addAttribute("mapMenu", mapMenu);
    }
}
