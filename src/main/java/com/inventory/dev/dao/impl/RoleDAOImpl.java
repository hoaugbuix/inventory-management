package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.RoleDAO;
import com.inventory.dev.entity.RoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleDAOImpl extends BaseDAOImpl<RoleEntity> implements RoleDAO<RoleEntity> {
}
