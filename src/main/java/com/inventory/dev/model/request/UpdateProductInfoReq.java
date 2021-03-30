package com.inventory.dev.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductInfoReq {
    private String code;

    private String name;

    private String description;

    private String imgUrl;

    private int activeFlag;

    private int cateId;

    private Timestamp updatedDate;
}
