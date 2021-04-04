package com.inventory.dev.model.dto;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto<UserEntity> {
    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
    private String username;
    private String password;
    private Set<RoleEntity> roles;
    private Set<UserRoleEntity> userRoles = new HashSet<>();
    private Integer roleID;
}
