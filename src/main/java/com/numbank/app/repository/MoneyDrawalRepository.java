package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.MoneyDrawal;

@Repository
public class MoneyDrawal extends AutoCRUD<MoneyDrawal, Integer>{
    
    @Override
    protected String getTableName() {
        return "moneyWithDrawal";
    }

    @Override
    protected MoneyDrawal mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new MoneyDrawal(
                resultSet.getInt("id"),
                resultSet.getDouble("amount"),
                resultSet.getTimestamp("withDrawalDate"),
                resultSet.getString("accountid")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
