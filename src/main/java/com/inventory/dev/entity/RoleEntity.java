package com.inventory.dev.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "role")
@Table(name = "role")
public class RoleEntity extends BaseEntity {
    @Column(name = "role_name", length = 20)
    private String roleName;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<AuthEntity> auths;
}
