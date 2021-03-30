package com.inventory.dev.model.request;

import com.inventory.dev.entity.CategoryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductInfoReq {
    @NotNull(message = "code trống")
    @NotEmpty(message = "code trống")
    @ApiModelProperty(
            example = "code-x",
            notes = "code trống",
            required = true
    )
    private String code;

    @NotNull(message = "name trống")
    @NotEmpty(message = "name trống")
    @ApiModelProperty(
            example = "bot giat",
            notes = "name trống",
            required = true
    )
    private String name;

    @NotNull(message = "Description trống")
    @NotEmpty(message = "Description trống")
    @ApiModelProperty(
            example = "This is description",
            notes = "Description trống",
            required = true
    )
    private String description;

    @NotNull(message = "imgUrl khoản trống")
    @NotEmpty(message = "imgUrl khoản trống")
    @ApiModelProperty(
            example = "/xxxx",
            notes = "imgUrl khoản trống",
            required = true
    )
    private String imgUrl;

    private CategoryEntity categories;

    @NotNull(message = "id  trống")
    @NotEmpty(message = "id trống")
    @ApiModelProperty(
            example = "1",
            notes = "id trống",
            required = true
    )
    private int cateId;


//    private int activeFlag;
//
//    private Timestamp createdDate;
//
//    private Timestamp updatedDate;
}
