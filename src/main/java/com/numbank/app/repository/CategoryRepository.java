package com.numbank.app.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.numbank.app.config.ConnectionDB;
import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Category;

@Repository
public class CategoryRepository extends AutoCRUD<Category, Integer>{
    private final Connection connection = ConnectionDB.createConnection();
    
    @Override
    protected String getTableName() {
        return "category";
    }
    
    @Override
    protected Category mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("type")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category save(Category toSave) {
        String sql = "INSERT INTO \"category\" (name, type) VALUES " +
                "( '" + toSave.getName() + "', '" + toSave.getType() + "') ;";

        try {
            connection.createStatement().executeUpdate(sql);
            return toSave;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }
}
