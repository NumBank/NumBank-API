package com.numbank.app.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.numbank.app.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import com.numbank.app.config.ConnectionDB;
import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Transaction;

@Repository
@AllArgsConstructor
public class TransactionRepository extends AutoCRUD<Transaction, String>{
    private final Connection connection = ConnectionDB.createConnection();
    private final AccountService accountService;
    
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
                resultSet.getTimestamp("dateeffect").toLocalDateTime(),
                resultSet.getTimestamp("savedate").toLocalDateTime(),
                resultSet.getBoolean("extern"),
                resultSet.getBoolean("status"),
                resultSet.getString("accountId"),
                resultSet.getInt("categoryId")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Transaction save(Transaction toSave) {
        String sql = "";

        if ("DEBIT".equals(toSave.getLabel())) {
            sql = "DO $$" +
                    "BEGIN" +
                    "   BEGIN" +
                    "       INSERT INTO balancehistory (value, accountId) VALUES " +
                    "           ( (" + accountService.getById(toSave.getAccountId()).getBalance() + " - " + toSave.getAmount() + "), " +
                    "              '" + toSave.getAccountId() + "' );" +
                    "       INSERT INTO \"transaction\" (id, label, amount, accountId,  categoryId) VALUES " +
                    "           ('"+ toSave.getId() +"', '" + toSave.getLabel() + "', " + toSave.getAmount() + ", '" + toSave.getAccountId() + "', " + toSave.getCategoryId() + ");" +
                    "       EXCEPTION" +
                    "           WHEN OTHERS THEN" +
                    "               ROLLBACK;" +
                    "               RAISE;" +
                    "   END;" +
                    "   COMMIT;" +
                    "END $$;";
        }
        
        if ("CREDIT".equals(toSave.getLabel())) {
            sql = "DO $$" +
                    "BEGIN" +
                    "   BEGIN" +
                    "       INSERT INTO balancehistory (value, accountId) VALUES " +
                    "           ( (" + accountService.getById(toSave.getAccountId()).getBalance() + " + " + toSave.getAmount() + "), " +
                    "            '" + toSave.getAccountId() + "' );" +
                    "       INSERT INTO \"transaction\" (id, label, amount, accountId, categoryId) VALUES " +
                    "           ('"+ toSave.getId() +"', '" + toSave.getLabel() + "', " + toSave.getAmount() + ", '" + toSave.getAccountId() + "', " + toSave.getCategoryId() + ");" +
                    "       EXCEPTION" +
                    "           WHEN OTHERS THEN" +
                    "               ROLLBACK;" +
                    "               RAISE;" +
                    "   END;" +
                    "   COMMIT;" +
                    "END $$;";
        }
        
        try {
            connection.createStatement().executeUpdate(sql);
            return toSave;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
