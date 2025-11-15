package com.course.dao;

import com.course.model.StudentStat;
import com.course.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Student-related DB operations used by the Swing admin.
 */
public class StudentDAO {

    /**
     * Returns all students with a count of their enrollments.
     */
    public List<StudentStat> getAllWithEnrollCount() throws SQLException {
        String sql = "SELECT s.student_id, s.username, s.full_name, s.email, s.phone, " +
                     "COALESCE(COUNT(e.enrollment_id),0) AS enrolled_count " +
                     "FROM students s LEFT JOIN enrollments e ON s.student_id = e.student_id " +
                     "GROUP BY s.student_id, s.username, s.full_name, s.email, s.phone " +
                     "ORDER BY s.student_id";
        List<StudentStat> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StudentStat s = new StudentStat();
                s.setStudentId(rs.getInt("student_id"));
                s.setUsername(rs.getString("username"));
                s.setFullName(rs.getString("full_name"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                s.setEnrolledCount(rs.getInt("enrolled_count"));
                list.add(s);
            }
        }
        return list;
    }
}
