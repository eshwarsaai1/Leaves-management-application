package com.wavemaker.leavemanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnector.class);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Leave_management_db",
                    "root", "EshwarDB@08");
        }
        catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Exception:", e);
        }
    }

    public static Connection getInstance() {
        return connection;
    }
}
