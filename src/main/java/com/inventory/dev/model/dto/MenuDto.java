package com.inventory.dev.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto extends AbstractDto<MenuDto> {
    private int parentId;
    private String url;
    private String name;
    private int orderIndex;
    private Set auths = new HashSet(0);
    private List<Menu> child;
    private String idMenu;
    private Map<Integer,Integer> mapAuth;
}
