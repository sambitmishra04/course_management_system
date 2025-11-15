package com.course.dao;

import com.course.util.DBConnection;
import com.course.model.EnrollmentDTO;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class EnrollmentDAO {

    /**
     * Returns set of course_ids the student is enrolled in.
     */
    public Set<Integer> getEnrolledCourseIds(int studentId) throws SQLException {
        Set<Integer> set = new HashSet<>();
        String sql = "SELECT course_id FROM enrollments WHERE student_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) set.add(rs.getInt("course_id"));
            }
        }
        return set;
    }

    /**
     * Enroll student in course (transactional): checks seats, inserts enrollment, decrements seats.
     * Returns true if enrolled successfully, false if seats unavailable or already enrolled.
     */
    public boolean enroll(int studentId, int courseId) throws SQLException {
        String checkEnrollSql = "SELECT 1 FROM enrollments WHERE student_id = ? AND course_id = ?";
        String checkSeatsSql = "SELECT seats FROM courses WHERE course_id = ? FOR UPDATE";
        String insertEnrollSql = "INSERT INTO enrollments (student_id, course_id, enroll_date, status) VALUES (?,?,CURDATE(),'enrolled')";
        String updateSeatsSql = "UPDATE courses SET seats = seats - 1 WHERE course_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // check already enrolled
            try (PreparedStatement ps = conn.prepareStatement(checkEnrollSql)) {
                ps.setInt(1, studentId); ps.setInt(2, courseId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        conn.rollback();
                        return false; // already enrolled
                    }
                }
            }

            // check seats (and lock row)
            int seats = 0;
            try (PreparedStatement ps = conn.prepareStatement(checkSeatsSql)) {
                ps.setInt(1, courseId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) seats = rs.getInt("seats");
                    else {
                        conn.rollback();
                        return false; // course not found
                    }
                }
            }

            if (seats <= 0) {
                conn.rollback();
                return false; // no seats
            }

            // insert enrollment
            try (PreparedStatement ps = conn.prepareStatement(insertEnrollSql)) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                ps.executeUpdate();
            }

            // decrement seats
            try (PreparedStatement ps = conn.prepareStatement(updateSeatsSql)) {
                ps.setInt(1, courseId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) try { conn.rollback(); } catch (SQLException e) { /* ignore */ }
            throw ex;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

    /**
     * De-enroll student from course: delete enrollment and increment seats.
     * Returns true if de-enrolled, false if not enrolled.
     */
    public boolean deEnroll(int studentId, int courseId) throws SQLException {
        String checkEnrollSql = "SELECT enrollment_id FROM enrollments WHERE student_id = ? AND course_id = ? FOR UPDATE";
        String deleteEnrollSql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        String updateSeatsSql = "UPDATE courses SET seats = seats + 1 WHERE course_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int enrollmentId = -1;
            try (PreparedStatement ps = conn.prepareStatement(checkEnrollSql)) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) enrollmentId = rs.getInt("enrollment_id");
                    else {
                        conn.rollback();
                        return false; // not enrolled
                    }
                }
            }

            // delete enrollment
            try (PreparedStatement ps = conn.prepareStatement(deleteEnrollSql)) {
                ps.setInt(1, enrollmentId);
                ps.executeUpdate();
            }

            // increment seats
            try (PreparedStatement ps = conn.prepareStatement(updateSeatsSql)) {
                ps.setInt(1, courseId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) try { conn.rollback(); } catch (SQLException e) { /* ignore */ }
            throw ex;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { /* ignore */ }
        }
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
