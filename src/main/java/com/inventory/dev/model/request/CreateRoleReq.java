package com.inventory.dev.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleReq {
    @NotNull(message = "Tên trống")
    @NotEmpty(message = "Tên trống")
    @ApiModelProperty(
            example = "Sam",
            notes = "Tên trống",
            required = true
    )
//    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "Họ trống")
    @NotEmpty(message = "Họ trống")
    @ApiModelProperty(
            example = "Nguyen",
            notes = "Họ trống",
            required = true
    )
//    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Avatar trống")
    @NotEmpty(message = "Avatar trống")
    @ApiModelProperty(
            example = "image.jpg",
            notes = "avatar trống",
            required = true
    )
//    @JsonProperty("avatar")
    private String avatar;

    @NotNull(message = "Email trống")
    @NotEmpty(message = "Email trống")
    @ApiModelProperty(
            example = "sam.smith@gmail.com",
            notes = "Email trống",
            required = true
    )
    private String roleName;

    @NotNull(message = "Tên tài khoản trống")
    @NotEmpty(message = "Tên tài khoản trống")
    @ApiModelProperty(
            example = "admin",
            notes = "Tên tài khoản trống",
            required = true
    )
    private String description;

    private Date updatedDate;

}
