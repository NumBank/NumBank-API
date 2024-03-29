package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Account;

@Repository
public class AccountRepository extends AutoCRUD<Account, String> {

    @Override
    protected String getTableName() {
        return "account";
    }

    @Override
    protected Account mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Account(
                resultSet.getString("id"),
                resultSet.getString("customerFirstName"),
                resultSet.getString("customerLastName"),
                resultSet.getDate("birthdate").toLocalDate(),
                resultSet.getDouble("netSalary"),
                resultSet.getString("number"),
                resultSet.getBoolean("debt"),
                0.0
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
