package com.inventory.dev.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthFormEntity {
    private int roleId;
    private int menuId;
    private int permission;
}
