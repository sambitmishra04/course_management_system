package com.course.dao;

import com.course.model.EnrollmentDTO;
import java.math.BigDecimal;
import java.sql.Date;
import com.course.model.StudentStat;
import com.course.util.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EnrollmentDAO {
    public int getEnrollmentCount(int courseId) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM enrollments WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt");
            }
        }
        return 0;
    }
    public List<StudentStat> getStudentsByCourse(int courseId) throws SQLException {
        String sql = "SELECT s.student_id, s.username, s.full_name, s.email, s.phone " +
                     "FROM enrollments e JOIN students s ON e.student_id = s.student_id " +
                     "WHERE e.course_id = ? ORDER BY s.full_name";
        List<StudentStat> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentStat s = new StudentStat();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setUsername(rs.getString("username"));
                    s.setFullName(rs.getString("full_name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    // enrolledCount not used here (single course)
                    list.add(s);
                }
            }
        }
        return list;
    }
    
    public List<EnrollmentDTO> getEnrollmentsByStudent(int studentId) throws SQLException {
        String sql = "SELECT e.enrollment_id, e.course_id, e.enroll_date, e.status, " +
                     "c.code AS course_code, c.title AS course_title, c.fee AS course_fee " +
                     "FROM enrollments e " +
                     "JOIN courses c ON e.course_id = c.course_id " +
                     "WHERE e.student_id = ? " +
                     "ORDER BY e.enroll_date DESC";

        List<EnrollmentDTO> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EnrollmentDTO dto = new EnrollmentDTO();
                    dto.setEnrollmentId(rs.getInt("enrollment_id"));
                    dto.setCourseId(rs.getInt("course_id"));
                    dto.setCourseCode(rs.getString("course_code"));
                    dto.setCourseTitle(rs.getString("course_title"));
                    dto.setEnrollDate(rs.getDate("enroll_date"));
                    dto.setStatus(rs.getString("status"));
                    dto.setFee(rs.getBigDecimal("course_fee"));
                    list.add(dto);
                }
            }
        }
        return list;
    }

}
