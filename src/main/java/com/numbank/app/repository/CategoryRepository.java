package com.numbank.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.numbank.app.model.AutoCRUD;
import com.numbank.app.model.entity.Category;

@Repository
public class CategoryRepository extends AutoCRUD<Category, String>{
    
    @Override
    protected String getTableName() {
        return "category";
    }
    
    @Override
    protected Category mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Category(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("type")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
