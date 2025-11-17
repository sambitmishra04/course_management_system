package JDBC2;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteRecord {
    public static void main(String[] args) {
        String sql = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DBConnection.getConnection("testdb3");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Bob");
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " record(s) deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
