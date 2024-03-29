package com.numbank.app.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.numbank.app.config.ConnectionDB;
import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.BalanceHistory;

@Repository
public class BalanceHistoryRepository extends AutoCRUD<BalanceHistory, String> {
    
    @Override
    protected String getTableName() {
        return "balancehistory";
    }

    @Override
    protected BalanceHistory mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new BalanceHistory(
                resultSet.getInt("id"),
                resultSet.getDouble("value"),
                resultSet.getTimestamp("updatedatetime"),
                resultSet.getString("accountid")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BalanceHistory getBalanceNow(String id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            String sql = "SELECT bh.* FROM \"account\" a INNER JOIN \"balancehistory\" bh ON bh.accountid = a.id " +
                        "WHERE a.id = '" + id + "' " +
                        "ORDER BY updatedatetime DESC " +
                        "LIMIT 1;";

            resultSet = statement.executeQuery(sql);
            BalanceHistory responseSQL = null;

            while (resultSet.next()) {
                responseSQL = new BalanceHistory(
                    resultSet.getInt("id"),
                    resultSet.getDouble("value"),
                    resultSet.getTimestamp("updatedatetime"),
                    resultSet.getString("accountid")
                );
            }
            return responseSQL;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<BalanceHistory> findAllByAccountId(String id, String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            List<BalanceHistory> responseSQL = new ArrayList<>();

            while (resultSet.next()) {
                responseSQL.add(new BalanceHistory(
                        resultSet.getInt("id"),
                        resultSet.getDouble("value"),
                        resultSet.getTimestamp("updatedatetime"),
                        resultSet.getString("accountid")
                ));
            }
            return responseSQL;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
