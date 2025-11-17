package JDBC2;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(50),"
                + "email VARCHAR(50)"
                + ")";

        try (Connection conn = DBConnection.getConnection("testdb3");
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Table 'users' created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
