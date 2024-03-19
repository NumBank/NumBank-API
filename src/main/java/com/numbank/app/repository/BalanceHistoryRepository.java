package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.BalanceHistory;

@Repository
public class BalanceHistoryRepository extends AutoCRUD<BalanceHistory, Integer> {
    
    @Override
    protected String getTableName() {
        return "balancehistory";
    }

    @Override
    protected BalanceHistory mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new BalanceHistory(
                resultSet.getInt("id"),
                resultSet.getDouble("balance"),
                resultSet.getTimestamp("updatedatetime"),
                resultSet.getInt("id_account")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
