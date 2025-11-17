package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteUser {
    public static void main(String[] args) {
        String sql = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Alice");

            int rows = pstmt.executeUpdate();
            System.out.println("Deleted " + rows + " row(s).");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
