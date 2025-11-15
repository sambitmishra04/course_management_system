package com.course.model;

/**
 * DTO representing a student and their enrollment count (used in admin UI).
 */
public class StudentStat {
    private int studentId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private int enrolledCount;

    public StudentStat() {}

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(int enrolledCount) { this.enrolledCount = enrolledCount; }
    
    @Override
    public String toString() {
        String name = (fullName != null && !fullName.trim().isEmpty()) ? fullName : username;
        return String.format("%s [%s] (%d)", name, username, enrolledCount);
    }
}
