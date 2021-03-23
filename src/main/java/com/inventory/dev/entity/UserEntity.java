package com.inventory.dev.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity(name = "user")
@Table(name = "user")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
@ApiModel(value = "User model")
public class UserEntity extends BaseEntity {
    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

//    @Column(name = "verification_code")
//    private String verificationCode;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;
}
