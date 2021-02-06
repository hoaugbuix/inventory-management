package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.MenuDAO;
import com.inventory.dev.entity.MenuEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class MenuDAOImpl extends BaseDAOImpl<MenuEntity> implements MenuDAO<MenuEntity> {
}
