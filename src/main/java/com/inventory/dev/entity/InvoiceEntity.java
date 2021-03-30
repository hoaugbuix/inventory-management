package com.inventory.dev.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "invoice")
@Table(name = "invoice")
public class InvoiceEntity extends BaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private int type;

    @Column(name = "qty")
    private int qty;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "from_date")
    private Date fromDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductInfoEntity productInfos;
}
