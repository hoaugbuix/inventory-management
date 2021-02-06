package com.inventory.dev.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "history")
@Table(name = "history")
public class HistoryEntity extends BaseEntity {
    @Column(name = "action_name")
    private String actionName;

    @Column(name = "type")
    private Integer type;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductInfoEntity productInfo;
}
