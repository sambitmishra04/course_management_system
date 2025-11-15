package com.course.dao;

import com.course.model.Student;
import com.course.util.DBConnection;

import java.sql.*;

public class StudentDAO {

    public void register(Student s) throws SQLException {
        String sql = "INSERT INTO students (username,password,full_name,email,phone) VALUES (?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getUsername());
            ps.setString(2, s.getPassword()); // TODO: hash in production
            ps.setString(3, s.getFullName());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getPhone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) s.setStudentId(rs.getInt(1));
            }
        }
    }

    public Student login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM students WHERE username = ? AND password = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setUsername(rs.getString("username"));
                    s.setFullName(rs.getString("full_name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    // Do NOT set password back into session object in production
                    return s;
                }
            }
        }
        return null;
    }

    public Student findById(int studentId) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setUsername(rs.getString("username"));
                    s.setFullName(rs.getString("full_name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    return s;
                }
            }
        }
        return null;
    }
}
