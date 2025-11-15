package com.course.dao;

import com.course.model.Course;
import com.course.model.CourseStat;
import com.course.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CourseDAO {

    // existing methods (getAll, addCourse) may remain — below are additions

    /**
     * Returns list of courses with enrolled counts (joined query).
     */
    public List<CourseStat> getAllWithEnrollCount() throws SQLException {
        String sql = "SELECT c.course_id, c.code, c.title, c.description, c.duration_weeks, c.fee, c.seats, " +
                     "COALESCE(COUNT(e.enrollment_id),0) AS enrolled_count " +
                     "FROM courses c LEFT JOIN enrollments e ON c.course_id = e.course_id " +
                     "GROUP BY c.course_id, c.code, c.title, c.description, c.duration_weeks, c.fee, c.seats " +
                     "ORDER BY c.course_id";
        List<CourseStat> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CourseStat s = new CourseStat();
                s.setCourseId(rs.getInt("course_id"));
                s.setCode(rs.getString("code"));
                s.setTitle(rs.getString("title"));
                s.setDescription(rs.getString("description"));
                s.setDurationWeeks(rs.getInt("duration_weeks"));
                s.setFee(rs.getBigDecimal("fee"));
                s.setSeats(rs.getInt("seats"));
                s.setEnrolledCount(rs.getInt("enrolled_count"));
                list.add(s);
            }
        }
        return list;
    }

    /**
     * Update seats and fee for a course. Also allow updating title/description if needed.
     */
    public void updateCourseSeatsAndFee(int courseId, int seats, BigDecimal fee) throws SQLException {
        String sql = "UPDATE courses SET seats = ?, fee = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, seats);
            ps.setBigDecimal(2, fee);
            ps.setInt(3, courseId);
            ps.executeUpdate();
        }
    }

    /**
     * Delete a course: delete related enrollments first to avoid FK problems, then remove the course.
     */
    public void deleteCourseById(int courseId) throws SQLException {
        String deleteEnrollSql = "DELETE FROM enrollments WHERE course_id = ?";
        String deleteCourseSql = "DELETE FROM courses WHERE course_id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(deleteEnrollSql)) {
                ps1.setInt(1, courseId);
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = conn.prepareStatement(deleteCourseSql)) {
                ps2.setInt(1, courseId);
                ps2.executeUpdate();
            }
            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) try { conn.rollback(); } catch (SQLException e) { /* ignore */ }
            throw ex;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

    /**
     * Optionally: update other course fields (title, code, description, duration).
     */
    public void updateCourseDetails(int courseId, String code, String title, String description, int durationWeeks, BigDecimal fee, int seats) throws SQLException {
        String sql = "UPDATE courses SET code=?, title=?, description=?, duration_weeks=?, fee=?, seats=? WHERE course_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setInt(4, durationWeeks);
            ps.setBigDecimal(5, fee);
            ps.setInt(6, seats);
            ps.setInt(7, courseId);
            ps.executeUpdate();
        }
    }
}
