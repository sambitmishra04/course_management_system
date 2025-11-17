package JDBC2;

import java.sql.Connection;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        String dbName = "testdb3";

        try (Connection conn = DBConnection.getConnection(null);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
