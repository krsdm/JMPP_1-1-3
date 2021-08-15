package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DB_NAME = "usersdb";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
