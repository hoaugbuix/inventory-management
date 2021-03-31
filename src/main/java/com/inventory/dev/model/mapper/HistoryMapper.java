package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.HistoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryMapper implements RowMapper<HistoryEntity>{
    @Override
    public HistoryEntity mapRow(ResultSet resultSet) {
       try {
           HistoryEntity history = new HistoryEntity();
           history.setActionName(resultSet.getString("action_name"));
           return history;
       }catch (SQLException e){
           return null;
       }
    }
}
