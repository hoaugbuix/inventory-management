package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.model.dto.UserDto;
import com.inventory.dev.model.request.CreateUserReq;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet resultSet) {
        try {
            UserEntity user = new UserEntity();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setAvatar(resultSet.getString("avatar"));
            user.setUsername(resultSet.getString("user_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setActiveFlag(resultSet.getInt("active_flag"));
            user.setCreatedDate(resultSet.getDate("created_date"));
            user.setUpdatedDate(resultSet.getDate("updated_date"));
//            try {
//                RoleEntity role = new RoleEntity();
//                role.setRoleName(resultSet.getString("role_name"));
//                role.setDescription(resultSet.getString("description"));
//                Set<RoleEntity> roleSet = new HashSet<>();
//                roleSet.add(role);
//                user.setRoles(roleSet);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            return user;
        }catch (SQLException e){
            return null;
        }
    }


    public static UserEntity toUserEntityReq(CreateUserReq req) {
        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setAvatar(req.getAvatar());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        //hash
        String hash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        user.setActiveFlag(1);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        return user;
    }


    public static UserEntity toUserEntity(UserDto req) {
        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
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
        tmp.setFirstName(user.getFirstName());
        tmp.setLastName(user.getLastName());
        tmp.setAvatar(user.getAvatar());
        tmp.setEmail(user.getEmail());
        tmp.setUsername(user.getUsername());
        tmp.setRoles(user.getRoles());
        tmp.setActiveFlag(user.getActiveFlag());
        tmp.setCreatedDate(user.getCreatedDate());
        return tmp;
    }


}
