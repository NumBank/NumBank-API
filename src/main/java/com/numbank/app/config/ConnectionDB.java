package com.numbank.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.numbank.app.exception.DatabaseConnectionException;

public class ConnectionDB {
    private static final String dbUrl = System.getenv("DB_URL");
    private static final String dbUsername = System.getenv("DB_USERNAME");
    private static final String dbPassword = System.getenv("DB_PASSWORD");

    public static Connection createConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    public static Connection createConnection() {
        return createConnection(dbUrl, dbUsername, dbPassword);
    }
}
