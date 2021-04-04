package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.HistoryDAO;
import com.inventory.dev.entity.HistoryEntity;
import com.inventory.dev.model.mapper.HistoryMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDAOImpl extends BaseDAOImpl<HistoryEntity> implements HistoryDAO<HistoryEntity> {

    @Override
    public List<HistoryEntity> findAllHistoryJdbc() {
        String sql = "select * from history";
        return queryJdbc(sql, new HistoryMapper());
    }

    @Override
    public Integer saveHistoryJdbc(HistoryEntity history) {
        StringBuilder sql = new StringBuilder("INSERT INTO history(action_name, type, product_id, qty, price, active_flag, created_date, updated_date)");
        sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(), history.getActionName(), history.getType(),
                history.getProductInfo(), history.getQty(), history.getPrice(), history.getActiveFlag(),
                history.getCreatedDate(), history.getUpdatedDate());
    }
}
