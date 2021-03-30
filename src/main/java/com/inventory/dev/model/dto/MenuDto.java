package com.inventory.dev.model.dto;

import com.inventory.dev.entity.AuthEntity;
import com.inventory.dev.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto extends AbstractDto<MenuDto> {
    private int parentId;
    private String url;
    private String name;
    private int orderIndex;
    private Set<AuthEntity> auths;
    private List<MenuDto> child;
    private String idMenu;
    private Map<Integer, Integer> mapAuth;
}
