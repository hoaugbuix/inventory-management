package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.AuthEntity;
import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.model.dto.MenuDto;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class MenuMapper {
    public static MenuEntity toMenuEntity(MenuDto dto) {
        MenuEntity menu = new MenuEntity();
        menu.setParentId(dto.getParentId());
        menu.setUrl(dto.getUrl());
        menu.setName(dto.getName());
        menu.setOrderIndex(dto.getOrderIndex());
        menu.setActiveFlag(1);
        menu.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        menu.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        menu.setAuths(dto.getAuths());
        return menu;
    }

    public static MenuDto toMenuDto(MenuEntity menu) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setParentId(menu.getParentId());
        dto.setUrl(menu.getUrl());
        dto.setName(menu.getName());
        dto.setOrderIndex(menu.getOrderIndex());
        dto.setActiveFlag(menu.getActiveFlag());
        dto.setCreatedDate(menu.getCreatedDate());
        dto.setUpdatedDate(menu.getUpdatedDate());
        Set<Integer> obj = new HashSet<>();
        for (AuthEntity auth: menu.getAuths()){
            obj.add(auth.getRoles().getId());
        }
        dto.setAuths(obj);
        return dto;
    }
}
