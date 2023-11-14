package com.urise.webapp.util;

import com.urise.webapp.sql.ConnectionFactory;

@FunctionalInterface
public interface SqlConnection {
    void execute(ConnectionFactory connectionFactory);
}
