package com.inventory.dev.service.impl;

import com.inventory.dev.dao.UserDAO;
import com.inventory.dev.dao.UserRoleDAO;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.entity.UserRoleEntity;
import com.inventory.dev.exception.DuplicateRecordException;
import com.inventory.dev.model.mapper.UserMapper;
import com.inventory.dev.model.request.CreateUserReq;
import com.inventory.dev.repository.RoleRepository;
import com.inventory.dev.repository.UserRepository;
import com.inventory.dev.service.UserService;
import com.inventory.dev.util.HashingPassword;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserServiceImpl implements UserService {
    final static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO<UserEntity> userDAO;

    @Autowired
    private UserRoleDAO<UserRoleEntity> userRoleDAO;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserEntity> findByProperty(String property, Object value) {
        log.info("Find user by property start ");
        return userDAO.findByProperty(property, value);

    }

    @Override
    public UserEntity findById(int id) {
        log.info("Find user by id ");
        return userDAO.findById(UserEntity.class, id);
    }

    @Override
    public UserEntity createUser(CreateUserReq req) {
        // Check email exist
        UserEntity users = userRepository.findByEmailAndUsername(req.getEmail(), req.getUsername());
        if (users.getEmail().equals(req.getEmail())) {
            throw new DuplicateRecordException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác.");
        }

        users = UserMapper.toUserEntityReq(req);

        users.setRoles(Collections.singleton(roleRepository.findByRoleName("user")));


        userRepository.saveAndFlush(users);

        return users;
    }

    @Override
    public void save(UserEntity user) {
        user.setActiveFlag(1);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        user.setPassword(HashingPassword.encrypt(user.getPassword()));
        userDAO.save(user);
        RoleEntity role = new RoleEntity();
        UserRoleEntity userRole = new UserRoleEntity();
//        userRole.setUsers(user);
//        userRole.setRoles(role);
        userRole.setActiveFlag(1);
        userRole.setCreatedDate(new Date());
        userRole.setUpdatedDate(new Date());
        userRoleDAO.save(userRole);
    }

    @Override
    public void update(UserEntity users) {
        UserEntity user = findById(users.getId());
        if (user != null) {
            UserRoleEntity userRole =(UserRoleEntity) user.getUserRoles().iterator().next();
            RoleEntity role = userRole.getRoles();
            role.setId(users.getId());
            userRole.setRoles(role);
            userRole.setUpdatedDate(new Date());
            user.setFirstName(users.getFirstName());
            user.setLastName(users.getLastName());
            user.setEmail(users.getEmail());
            user.setUsername(users.getUsername());
            user.setUpdatedDate(new Date());
            userRoleDAO.update(userRole);
        }
        userDAO.update(users);
    }

    @Override
    public void deleteUser(UserEntity user) {
        user.setActiveFlag(0);
        user.setUpdatedDate(new Date());
        userDAO.update(user);
    }

    @Override
    public List<UserEntity> getUsersList(UserEntity users, Paging paging) {
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if (users != null) {
            if (!StringUtils.isEmpty(users.getFirstName())) {
                queryStr.append(" and model.firstName like :firstName");
                mapParams.put("firstName", "%" + users.getFirstName() + "%");
            }
            if (!StringUtils.isEmpty(users.getLastName())) {
                queryStr.append(" and model.lastName like :lastName");
                mapParams.put("lastName", "%" + users.getLastName() + "%");
            }
            if (!StringUtils.isEmpty(users.getUsername())) {
                queryStr.append(" and model.username like :username");
                mapParams.put("username", "%" + users.getUsername() + "%");
            }
        }
        return userDAO.findAll(queryStr.toString(), mapParams, paging);
    }
}
