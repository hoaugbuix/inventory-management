package com.inventory.dev.service.impl;

import com.inventory.dev.dao.RoleDAO;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleServiceImpl implements RoleService {
    final static Logger log = Logger.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleDAO<RoleEntity> roleDAO;

    public List<RoleEntity> getRoleList(RoleEntity role, Paging paging) {

        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        log.info("getRoleList" + mapParams.toString());
        return roleDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    public void saveRole(RoleEntity role) throws Exception {
        log.info("Insert role " + role.toString());
        role.setActiveFlag(1);
        role.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        roleDAO.save(role);
    }

    public void updateRole(RoleEntity role) throws Exception {
        log.info("Update role " + role.toString());
        role.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        roleDAO.update(role);
    }

    @Override
    public void deleteRole(RoleEntity role) throws Exception {
        role.setActiveFlag(0);
        role.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        log.info("Delete role " + role.toString());
        roleDAO.update(role);
    }

    public List<RoleEntity> findRole(String property, Object value) {
        log.info("=====Find by property role start====");
        log.info("property =" + property + " value" + value.toString());
        return roleDAO.findByProperty(property, value);
    }

    public RoleEntity findByIdRole(int id) {
        log.info("find role by id =" + id);
        return roleDAO.findById(RoleEntity.class, id);
    }
}
