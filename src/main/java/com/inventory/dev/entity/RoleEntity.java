package com.inventory.dev.entity;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "description", nullable = true, length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles")
    private Set<UserEntity> users;

}
