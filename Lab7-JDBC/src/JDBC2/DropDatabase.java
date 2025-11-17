package JDBC2;

import java.sql.Connection;
import java.sql.Statement;

public class DropDatabase {
    public static void main(String[] args) {
        String dbName = "testdb3";

        try (Connection conn = DBConnection.getConnection(null);
             Statement stmt = conn.createStatement()) {

            String sql = "DROP DATABASE IF EXISTS " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Database '" + dbName + "' dropped successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
