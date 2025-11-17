package JDBC2;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertRecord {
    public static void main(String[] args) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection("testdb3");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Alice");
            pstmt.setString(2, "alice@example.com");
            pstmt.executeUpdate();

            pstmt.setString(1, "Bob");
            pstmt.setString(2, "bob@example.com");
            pstmt.executeUpdate();

            System.out.println("Records inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
