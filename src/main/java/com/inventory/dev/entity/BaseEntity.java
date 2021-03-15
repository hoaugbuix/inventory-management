package com.inventory.dev.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated User ID")
    @Column(name = "id")
    private Integer id;

    @Column(name = "active_flag", columnDefinition = "Integer default 1")
    private int activeFlag;

    @Column(name = "created_date", nullable = true)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updated_date", nullable = true)
    @LastModifiedDate
    private Date updatedDate;
}
