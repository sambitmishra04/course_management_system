package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateUser {
    public static void main(String[] args) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Alice");
            pstmt.setString(2, "alice@example.com");

            int rows = pstmt.executeUpdate();
            System.out.println("Inserted " + rows + " row(s).");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
