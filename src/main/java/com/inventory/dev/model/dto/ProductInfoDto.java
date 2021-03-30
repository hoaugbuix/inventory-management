package com.inventory.dev.model.dto;

import com.inventory.dev.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto extends AbstractDto<ProductInfoDto> {
    private CategoryEntity category;
    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private Set histories = new HashSet(0);
    private Set productInStocks = new HashSet(0);
    private Set invoices = new HashSet(0);
    private MultipartFile multipartFile;
    private Integer cateId;
}
