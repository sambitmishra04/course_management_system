package JDBC2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectRecords {
    public static void main(String[] args) {
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection("testdb3");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
