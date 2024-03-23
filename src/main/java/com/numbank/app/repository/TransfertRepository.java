package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Transfert;

@Repository
public class TransfertRepository extends AutoCRUD<Transfert, String>{

    @Override
    protected String getTableName() {
        return "transfert";
    }

    @Override
    protected Transfert mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Transfert(
                resultSet.getString("id"),
                resultSet.getDouble("amount"),
                resultSet.getString("label"),
                resultSet.getTimestamp("dateeffect"),
                resultSet.getTimestamp("savedate"),
                resultSet.getBoolean("extern"),
                resultSet.getBoolean("status"),
                resultSet.getString("accountIdSender"),
                resultSet.getString("accountIdRecipient"),
                resultSet.getString("categoryId")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}