package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Transaction;

@Repository
public class TransactionRepository extends AutoCRUD<Transaction, String>{
    
    @Override
    protected String getTableName() {
        return "transaction";
    }
    
    @Override
    protected Transaction mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Transaction(
                resultSet.getString("id"),
                resultSet.getDouble("amount"),
                resultSet.getString("label"),
                resultSet.getTimestamp("dateeffect"),
                resultSet.getTimestamp("savedate"),
                resultSet.getBoolean("extern"),
                resultSet.getBoolean("status"),
                resultSet.getInt("id_account_sender"),
                resultSet.getInt("id_account_recipient"),
                resultSet.getInt("id_category")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
