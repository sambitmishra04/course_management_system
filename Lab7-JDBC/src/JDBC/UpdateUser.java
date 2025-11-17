package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateUser {
    public static void main(String[] args) {
        String sql = "UPDATE users SET email = ? WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "alice_new@example.com");
            pstmt.setString(2, "Alice");

            int rows = pstmt.executeUpdate();
            System.out.println("Updated " + rows + " row(s).");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
