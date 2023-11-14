package com.urise.webapp.util;

import com.urise.webapp.sql.ConnectionFactory;

import java.sql.DriverManager;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

}
