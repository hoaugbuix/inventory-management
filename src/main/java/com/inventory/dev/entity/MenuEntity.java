package com.inventory.dev.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "menu")
@Table(name = "menu")
public class MenuEntity extends BaseEntity {
    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private int orderIndex;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "menus")
    private Set<AuthEntity> auths;
}
