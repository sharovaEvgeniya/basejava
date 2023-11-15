package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void connectWithException(String sqlRequest) {
        connectWithException(sqlRequest, ps -> ps.execute());
    }

    public <T> T connectWithException(String sqlRequest, SqlConnection<T> sqlConnection) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRequest)) {
            return sqlConnection.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> void connectWithException(String uuid, String sqlRequest, SqlConnection<T> sqlConnection) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRequest)) {
            sqlConnection.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }
}
