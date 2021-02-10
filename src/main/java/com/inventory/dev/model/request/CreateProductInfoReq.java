package com.inventory.dev.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductInfoReq {
    @NotNull(message = "Tên trống")
    @NotEmpty(message = "Tên trống")
    @ApiModelProperty(
            example = "Sam",
            notes = "Tên trống",
            required = true
    )
    private String code;

    @NotNull(message = "Họ trống")
    @NotEmpty(message = "Họ trống")
    @ApiModelProperty(
            example = "Nguyen",
            notes = "Họ trống",
            required = true
    )
    private String name;

    @NotNull(message = "Avatar trống")
    @NotEmpty(message = "Avatar trống")
    @ApiModelProperty(
            example = "image.jpg",
            notes = "avatar trống",
            required = true
    )
    private String description;

    @NotNull(message = "Tên tài khoản trống")
    @NotEmpty(message = "Tên tài khoản trống")
    @ApiModelProperty(
            example = "admin",
            notes = "Tên tài khoản trống",
            required = true
    )
    private String url;

    private Date updatedDate;

}
