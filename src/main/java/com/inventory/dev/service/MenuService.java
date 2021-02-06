package com.inventory.dev.service;

import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.entity.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {
    public List<MenuEntity> getListMenu(Paging paging, MenuEntity menu);

    public void changeStatus(Integer id) throws Exception;

    public void updatePermission(int roleId, int menuId, int permission) throws Exception;
}
