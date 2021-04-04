package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.MenuDAO;
import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.model.mapper.MenuMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class MenuDAOImpl extends BaseDAOImpl<MenuEntity> implements MenuDAO<MenuEntity> {
    @Override
    public List<MenuEntity> getAllMenuJdbc() {
        String sql = "Select * from menu";
        return queryJdbc(sql, new MenuMapper());
    }

    @Override
    public MenuEntity findOneMenuJdbc(int id) {
        String sql = "select * from menu where id = ?";
        List<MenuEntity> menu = queryJdbc(sql, new MenuMapper(), id);
        return menu.isEmpty() ? null : menu.get(0);
    }

    @Override
    public Integer saveMenuJdbc(MenuEntity instance) {
        return null;
    }

    @Override
    public void updateMenuJdbc(MenuEntity menu) {
        StringBuilder sql = new StringBuilder("UPDATE menu SET parent_id = ?, url = ?, name= ?, order_index = ?, active_flag = ?, created_date = ?, updated_date = ?");
        updateJdbc(sql.toString(), menu.getParentId(), menu.getUrl(), menu.getName(), menu.getOrderIndex(),
                menu.getActiveFlag(), menu.getCreatedDate(), menu.getUpdatedDate());
    }
}
