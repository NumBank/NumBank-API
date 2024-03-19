package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Account;

public class AccountRepository extends AutoCRUD<Account, String> {

    @Override
    protected String getTableName() {
        return "account";
    }

    @Override
    protected Account mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Account(
                resultSet.getInt("id"),
                resultSet.getString("customerFirstName"),
                resultSet.getString("customerLastName"),
                resultSet.getDate("birthdate"),
                resultSet.getDouble("netSalary"),
                resultSet.getString("number"),
                resultSet.getBoolean("debtActivate")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
