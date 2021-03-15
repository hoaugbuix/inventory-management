package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.model.dto.MenuDto;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

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
        return menu;
    }

    public static MenuDto toMenuDto(MenuEntity menu) {
        MenuDto dto = new MenuDto();
        dto.setParentId(menu.getParentId());
        dto.setUrl(menu.getUrl());
        return dto;
    }
}
