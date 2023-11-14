package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.storage.SqlStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static void connectWithException(String sqlRequest) {
        connectWithException(sqlRequest, ps -> ps.execute());
    }

    public static <T> T connectWithException(String sqlRequest, SqlConnection<T> sqlConnection) {
        try (Connection connection = SqlStorage.connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRequest)) {
            return sqlConnection.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static <T> T connectWithException(String uuid, String sqlRequest, SqlConnection<T> sqlConnection) {
        try (Connection connection = SqlStorage.connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlRequest)) {
            return sqlConnection.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }
}
