package com.numbank.app.model;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.numbank.app.config.ConnectionDB;

public abstract class AutoCRUD<T, ID> implements CRUD<T, ID>{

    protected abstract String getTableName();
    
    protected abstract T mapResultSetToEntity(ResultSet resultSet);
    
    @Override
    public T getById(ID id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UUID _id;
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";

        try {
            connection = ConnectionDB.createConnection();
            preparedStatement = connection.prepareStatement(query);
            
            if (id.getClass().equals(query.getClass())) {
                _id = UUID.fromString((String) id);
                preparedStatement.setObject(1, _id);
            } else {
                preparedStatement.setObject(1, id);
            }
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + getTableName();
        List<T> listAll = new ArrayList<>();

        try {
            connection = ConnectionDB.createConnection();
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listAll.add(mapResultSetToEntity(resultSet));
            }
            return listAll;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<T> saveAll(List<T> toSaves) {
        List<T> allSaves = new ArrayList<>();
        for (T toSave : toSaves) {
            allSaves.add(save(toSave));
        }
        return allSaves;
    }

    @Override
    public T save(T toSave) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            Class<?> toSaveClass = toSave.getClass();

            Field[] fields = toSaveClass.getDeclaredFields();
    
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + getTableName() + " (");
                for (Field field : fields) {
                    queryBuilder.append(field.getName()).append(", ");
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
                queryBuilder.append(") VALUES ( ");
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(toSave);
                    queryBuilder.append("'" + value + "', ");
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
                queryBuilder.append(")");
    
                String insertQuery = queryBuilder.toString();

            statement.executeUpdate(insertQuery);
            return toSave;

        } catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public T update(T toUpdate) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionDB.createConnection();
            statement = connection.createStatement();

            Class<?> toUpdateClass = toUpdate.getClass();

            Field[] fields = toUpdateClass.getDeclaredFields();
    
            StringBuilder queryBuilder = new StringBuilder("UPDATE " + getTableName() + " SET ");
                String id = null;
                for (Field field : fields) {
                    if (field.getName() == "id")
                        id = (String) field.get("id");
                    field.setAccessible(true);
                    Object value = field.get(toUpdate);
                    queryBuilder.append(field.getName()).append(" = '" + value + "', " );
                }
                queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
                queryBuilder.append("WHERE id = " + id + ";");
    
                String updateQuery = queryBuilder.toString();

            statement.executeUpdate(updateQuery);
            return toUpdate;

        } catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
