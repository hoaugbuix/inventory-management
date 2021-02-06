package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.UserDAO;
import com.inventory.dev.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<UserEntity> implements UserDAO<UserEntity> {
}
