package com.numbank.app.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.config.ConnectionDB;
import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Transfert;

@Repository
public class TransfertRepository extends AutoCRUD<Transfert, String>{
    private final Connection connection = ConnectionDB.createConnection();

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
                resultSet.getTimestamp("dateeffect").toLocalDateTime(),
                resultSet.getTimestamp("savedate").toLocalDateTime(),
                resultSet.getBoolean("extern"),
                resultSet.getBoolean("status"),
                resultSet.getString("accountIdSender"),
                resultSet.getString("accountIdRecipient")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transfert save(Transfert toSave) {
        String sql = "INSERT INTO \"transfert\" (id, amount, label, accountidsender, accountidrecipient) VALUES " +
        "( '" + toSave.getId() + "', " + toSave.getAmount() + ", '" + toSave.getLabel() + "' , '" + toSave.getAccountIdSender() + "' , '" + toSave.getAccountIdRecipient() + "') ;";

        try {
            connection.createStatement().executeUpdate(sql);
            return toSave;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}