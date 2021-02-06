package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.UserRoleDAO;
import com.inventory.dev.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRoleDAOImpl extends BaseDAOImpl<UserRoleEntity> implements UserRoleDAO<UserRoleEntity> {
}
