package JDBC2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";      // change to your username
    private static final String PASSWORD = "MySQL@2024";  // change to your password

    public static Connection getConnection(String dbName) throws SQLException {
        String url = (dbName == null || dbName.isEmpty()) ? URL : URL + dbName;
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}
