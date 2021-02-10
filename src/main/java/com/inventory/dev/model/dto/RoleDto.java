package com.inventory.dev.model.dto;

import com.inventory.dev.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class RoleDto extends AbstractDto<RoleEntity> {
    private String roleName;
    private String description;
    private Set auths = new HashSet(0);
    private Set userRoles = new HashSet(0);
}
