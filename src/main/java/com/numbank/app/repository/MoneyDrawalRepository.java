package com.numbank.app.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.numbank.app.config.ConnectionDB;
import com.numbank.app.model.entity.MoneyDrawal;
import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;

@Repository
public class MoneyDrawalRepository extends AutoCRUD<MoneyDrawal, Integer>{
    
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

    public MoneyDrawal getByAccountIdNow(String id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            String sql = "SELECT mwd.* FROM \"account\" a INNER JOIN \"moneyWithDrawal\" mwd ON mwd.accountid = a.id " +
                    "WHERE a.id = '" + id + "' " +
                    "ORDER BY withDrawalDate DESC" +
                    "LIMIT 1 ;";

            resultSet = statement.executeQuery(sql);
            MoneyDrawal responseSQL = null;

            while (resultSet.next()) {
                responseSQL = new MoneyDrawal(
                        resultSet.getInt("id"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("withDrawalDate"),
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

    public List<MoneyDrawal> findAllByAccountId(String id, String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            List<MoneyDrawal> responseSQL = new ArrayList<>();

            while (resultSet.next()) {
                responseSQL.add(new MoneyDrawal(
                        resultSet.getInt("id"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("withDrawalDate"),
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
