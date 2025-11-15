package com.course.dao;

import com.course.model.Course;
import com.course.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CourseDAO {

    public List<Course> getAll() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Course co = new Course();
                co.setCourseId(rs.getInt("course_id"));
                co.setCode(rs.getString("code"));
                co.setTitle(rs.getString("title"));
                co.setDescription(rs.getString("description"));
                co.setDurationWeeks(rs.getInt("duration_weeks"));
                co.setFee(rs.getBigDecimal("fee"));
                co.setSeats(rs.getInt("seats"));
                list.add(co);
            }
        }
        return list;
    }

    public void addCourse(Course c) throws SQLException {
        String sql = "INSERT INTO courses (code,title,description,duration_weeks,fee,seats) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCode());
            ps.setString(2, c.getTitle());
            ps.setString(3, c.getDescription());
            ps.setInt(4, c.getDurationWeeks());
            ps.setBigDecimal(5, c.getFee());
            ps.setInt(6, c.getSeats());
            ps.executeUpdate();
        }
    }
}
