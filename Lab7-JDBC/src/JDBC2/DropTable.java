package JDBC2;

import java.sql.Connection;
import java.sql.Statement;

public class DropTable {
    public static void main(String[] args) {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection conn = DBConnection.getConnection("testdb3");
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Table 'users' dropped successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
