package com.inventory.dev.model.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AbstractDto<T> {
    private Integer id;
    private Integer activeFlag;
    private Date createdDate;
    private Date updatedDate;
//    private List<T> listResult = new ArrayList<>();
}
