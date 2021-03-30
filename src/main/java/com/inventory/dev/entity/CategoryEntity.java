package com.inventory.dev.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@ApiModel(value = "Category Model")
@Entity(name = "category")
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

//    @OneToMany(cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            mappedBy = "product_id")
//    private Set<ProductInfoEntity> productInfos;
}
