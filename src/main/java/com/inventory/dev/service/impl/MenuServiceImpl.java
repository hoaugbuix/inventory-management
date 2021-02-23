package com.inventory.dev.service.impl;

import com.inventory.dev.dao.AuthDAO;
import com.inventory.dev.dao.MenuDAO;
import com.inventory.dev.entity.AuthEntity;
import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.model.dto.MenuDto;
import com.inventory.dev.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuServiceImpl implements MenuService {
    private static final Logger log = Logger.getLogger(MenuServiceImpl.class);
    @Autowired
    private MenuDAO<MenuEntity> menuDAO;
    @Autowired
    private AuthDAO<AuthEntity> authDAO;

    @Override
    public List<MenuEntity> getListMenu(Paging paging, MenuEntity menu) {
        log.info("show all menu");
        StringBuilder queryStr = new StringBuilder();
        queryStr.append(" or model.activeFlag=0");
        Map<String, Object> mapParams = new HashMap<>();
        if (menu != null) {
            if (!StringUtils.isEmpty(menu.getUrl())) {
                queryStr.append(" and model.url like :url");
                mapParams.put("url", "%" + menu.getUrl() + "%");
            }
        }
        return menuDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        MenuEntity menu = menuDAO.findById(MenuEntity.class, id);
        if (menu != null) {
            menu.setActiveFlag(menu.getActiveFlag() == 1 ? 0 : 1);
            menuDAO.update(menu);
            return;
        }
    }

    @Override
    public void updatePermission(int roleId, int menuId, int permission) throws Exception {
        AuthEntity auth = authDAO.find(roleId, menuId);
        if (auth != null) {
            auth.setPermission(permission);
            authDAO.update(auth);
        } else {
            if (permission == 1) {
                auth = new AuthEntity();
                auth.setActiveFlag(1);
                RoleEntity role = new RoleEntity();
                role.setId(roleId);
                MenuEntity menu = new MenuEntity();
                menu.setId(menuId);
                auth.setRoles(role);
                auth.setMenus(menu);
                auth.setPermission(1);
                auth.setCreatedDate(new Date());
                auth.setUpdatedDate(new Date());
                authDAO.save(auth);
            }
        }
    }
}
