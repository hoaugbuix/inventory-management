package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.HistoryDAO;
import com.inventory.dev.entity.HistoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDAOImpl extends BaseDAOImpl<HistoryEntity> implements HistoryDAO<HistoryEntity> {
}
