package com.inventory.dev.model.mapper;

import com.inventory.dev.entity.InvoiceEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceMapper implements RowMapper<InvoiceEntity> {
    @Override
    public InvoiceEntity mapRow(ResultSet resultSet) {
        try {
            InvoiceEntity invoice  = new InvoiceEntity();
            invoice.setId(resultSet.getInt("id"));
            return invoice;
        }catch (SQLException e) {
            return null;
        }
    }
}
