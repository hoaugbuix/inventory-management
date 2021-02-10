package com.inventory.dev.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
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
    private Integer parentId;

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private Integer orderIndex;

}
