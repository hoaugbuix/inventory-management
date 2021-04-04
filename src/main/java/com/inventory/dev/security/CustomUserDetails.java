package com.inventory.dev.security;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    static final Logger logger = Logger.getLogger(CustomUserDetails.class);

    private UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info(user);
        Set<GrantedAuthority> roles = new HashSet<>();
        for (RoleEntity role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
//        logger.info("authorities : {}" + grantedAuthorities);
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
