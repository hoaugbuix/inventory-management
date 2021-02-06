package com.inventory.dev.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateUserReq {
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
    @Email(message = "Email không đúng định dạng")
    @ApiModelProperty(
            example = "sam.smith@gmail.com",
            notes = "Email trống",
            required = true
    )
    private String email;

    @NotNull(message = "Tên tài khoản trống")
    @NotEmpty(message = "Tên tài khoản trống")
    @ApiModelProperty(
            example = "admin",
            notes = "Tên tài khoản trống",
            required = true
    )
//    @JsonProperty("user_name")
    private String username;

    @NotNull(message = "Mật khẩu trống")
    @NotEmpty(message = "Mật khẩu trống")
    @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
    @ApiModelProperty(
            example = "verysecretpassword",
            notes = "Mật khẩu trống",
            required = true
    )
    private String password;

//    private int activeFlag;

//    @NotNull(message = "Role trống")
//    @ApiModelProperty(
//            example="[admin]",
//            notes="Role trống",
//            required=true
//    )
//    @JsonProperty("roles")
////    private ArrayList<String> roles;
//    private Collection<String> roles;
}
