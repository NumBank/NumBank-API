package com.numbank.app.repository;

import com.numbank.app.config.ConnectionDB;
import com.numbank.app.model.entity.AuthorisationOpen;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AutorizationOpenRepository extends AutoCRUD<AutorizationOpen, Integer> {

    protected String getTableName() {
        return "autorizationopen";
    }

    protected AuthorisationOpen mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new AuthorisationOpen(
                    resultSet.getString("id"),
                    resultSet.getDouble("approvedCredit")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AuthorisationOpen> findAllByAccountId(String id, String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            List<AuthorisationOpen> responseSQL = new ArrayList<>();

            while (resultSet.next()) {
                responseSQL.add(new AuthorisationOpen(
                        resultSet.getString("id"),
                        resultSet.getDouble("approvedCredit")
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
