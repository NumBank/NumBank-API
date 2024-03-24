package com.numbank.app.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.config.ConnectionDB;
import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Transaction;

@Repository
public class TransactionRepository extends AutoCRUD<Transaction, String>{
    private final Connection connection = ConnectionDB.createConnection();
    private BalanceHistoryRepository balanceRepo = new BalanceHistoryRepository();
    
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
                resultSet.getString("accountId"),
                resultSet.getString("categoryId")
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
                    "           ( (" + balanceRepo.getBalanceNow(toSave.getAccountId()).getValue() + " - " + toSave.getAmount() + "), " +
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
                    "           ( (" + balanceRepo.getBalanceNow(toSave.getAccountId()).getValue() + " + " + toSave.getAmount() + "), " +
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
