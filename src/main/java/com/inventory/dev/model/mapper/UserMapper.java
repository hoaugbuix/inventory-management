package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.model.dto.UserDto;
import com.inventory.dev.model.request.CreateUserReq;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {
    public static UserEntity toUserEntity(CreateUserReq req) {
        UserEntity user = new UserEntity();
        user.setFistName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setAvatar(req.getAvatar());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        //hash
        String hash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        user.setActiveFlag(1);
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        //set role
//        ArrayList<RoleEntity> roles = new ArrayList<>();
//        for (String roleName: req.getRoles()){
//            RoleEntity role = new RoleEntity();
//            role.setRoleName();
//            role.setActiveFlag(1);
//            role.setCreatedDate(new Date(System.currentTimeMillis()));
//            roles.add(role);
//            user.setRoles(Collections.singletonList(roleRepository.findByRoleName("user")));
//        }

        return user;
    }

    public static UserDto toUserDto(UserEntity user) {
        UserDto tmp = new UserDto();
        tmp.setId(user.getId());
        tmp.setFirstName(user.getFistName());
        tmp.setLastName(user.getLastName());
        tmp.setAvatar(user.getAvatar());
        tmp.setEmail(user.getEmail());
        tmp.setUsername(user.getUsername());
        Set<String> obj = new HashSet<>();
        for (RoleEntity r : user.getRoles()) {
            obj.add(r.getRoleName());
        }
        tmp.setRoles(obj);
        tmp.setActiveFlag(user.getActiveFlag());
        tmp.setCreatedDate(user.getCreatedDate());
        return tmp;
    }
}
