package com.inventory.dev.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "product_in_stock")
@Table(name = "product_in_stock")
public class ProductInStockEntity extends BaseEntity {
    @Column(name = "qty")
    private int qty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductInfoEntity productInfos;
}
