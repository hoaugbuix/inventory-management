package com.inventory.dev.controller;

import com.inventory.dev.entity.*;
import com.inventory.dev.model.dto.MenuDto;
import com.inventory.dev.model.mapper.MenuMapper;
import com.inventory.dev.service.MenuService;
import com.inventory.dev.service.RoleService;
import com.inventory.dev.util.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class MenuController {
    static final Logger log = Logger.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = {"/menu/list", "/menu/list/"})
    public String redirect() {
        return "redirect:/menu/list/1";
    }

    @RequestMapping("/menu/list/{page}")
    public ResponseEntity<List<MenuDto>> menuList(@PathVariable("page") int page, MenuEntity menu) {
        Paging paging = new Paging(15);
        paging.setIndexPage(page);
        List<MenuEntity> menuList = menuService.getListMenu(paging, menu);
        List<RoleEntity> roles = roleService.getRoleList(null, null);
        Collections.sort(roles, (o1, o2) -> o1.getId() - o2.getId());
        List<MenuDto> listDto = new ArrayList<>();
        for (MenuEntity item : menuList){
            Map<Integer, Integer> mapAuth = new TreeMap<>();
            for (RoleEntity role : roles) {
                mapAuth.put(role.getId(), 0);// 1-0 ,2-0,3-0
            }
            for (Object obj : item.getAuths()) {
                AuthEntity auth = (AuthEntity) obj;
                mapAuth.put(auth.getRoles().getId(), auth.getPermission());
            }
            MenuDto dto = MenuMapper.toMenuDto(item);
//            dto.setMapAuth(mapAuth);
           listDto.add(dto);
        }

        return ResponseEntity.ok(listDto);
    }

    @GetMapping("/menu/change-status/{id}")
    public ResponseEntity<?> change(@PathVariable("id") int id) {
        try {
            menuService.changeStatus(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("/menu/permission")
    public Object permission() {
        return initSelectbox();
    }

    @PostMapping("/menu/update-permission")
    public ResponseEntity<?> updatePermission(@Valid @RequestBody AuthFormEntity authForm) {
        try {
            menuService.updatePermission(authForm.getRoleId(), authForm.getMenuId(), authForm.getPermission());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(Constant.MSG_SUCCESS);
    }

    private Object initSelectbox() {
        List<RoleEntity> roles = roleService.getRoleList(null, null);
        List<MenuEntity> menus = menuService.getListMenu(null, null);
        Map<Integer, String> mapRole = new HashMap<>();
        Map<Integer, String> mapMenu = new HashMap<>();
        Map<Object, Object> newData = new HashMap<>();
        for (RoleEntity role : roles) {
            mapRole.put(role.getId(), role.getRoleName());
        }
        for (MenuEntity menu : menus) {
            mapMenu.put(menu.getId(), menu.getUrl());
        }

        newData.put(mapRole, mapMenu);
        return newData;
    }
}
