package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlConnection<T> {
    T execute(PreparedStatement ps) throws SQLException;
}