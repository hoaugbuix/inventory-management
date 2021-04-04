package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.AuthEntity;
import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.model.dto.MenuDto;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class MenuMapper implements RowMapper<MenuEntity> {
    public static MenuEntity toMenuEntity(MenuDto dto) {
        MenuEntity menu = new MenuEntity();
        menu.setId(dto.getId());
        menu.setParentId(dto.getParentId());
        menu.setUrl(dto.getUrl());
        menu.setName(dto.getName());
        menu.setOrderIndex(dto.getOrderIndex());
        menu.setActiveFlag(1);
        menu.setCreatedDate(new Date());
        menu.setUpdatedDate(new Date());
        menu.setAuths(dto.getAuths());
        return menu;
    }

    public static MenuDto toMenuDto(MenuEntity menu) {
        MenuDto dto = new MenuDto();
        if (menu != null){
            dto.setId(menu.getId());
            dto.setParentId(menu.getParentId());
            dto.setUrl(menu.getUrl());
            dto.setName(menu.getName());
            dto.setOrderIndex(menu.getOrderIndex());
            dto.setActiveFlag(menu.getActiveFlag());
            dto.setCreatedDate(menu.getCreatedDate());
            dto.setUpdatedDate(menu.getUpdatedDate());
//           dto.setAuths(menu.getAuths());
            Map<Integer, Integer> newObj = new HashMap<>();
            for (AuthEntity auth: menu.getAuths()){
                newObj.put(auth.getId(),auth.getPermission());
            }
            dto.setMapAuth(newObj);
        }
        return dto;
    }

    @Override
    public MenuEntity mapRow(ResultSet resultSet) {
        try {
            MenuEntity menu = new MenuEntity();
            menu.setId(resultSet.getInt("id"));
            return menu;
        }catch (SQLException e){
            return null;
        }
    }
}
