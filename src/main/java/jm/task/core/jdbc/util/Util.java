package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/rosses";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "1111";

    public static Connection getConectiction() {
        try {
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
