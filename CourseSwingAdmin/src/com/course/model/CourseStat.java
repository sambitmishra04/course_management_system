package com.course.model;

import java.math.BigDecimal;

public class CourseStat {
    private int courseId;
    private String code;
    private String title;
    private String description;
    private int durationWeeks;
    private BigDecimal fee;
    private int seats;
    private int enrolledCount; // number of students enrolled


    public CourseStat() {}

    // getters / setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(int durationWeeks) { this.durationWeeks = durationWeeks; }

    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public int getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(int enrolledCount) { this.enrolledCount = enrolledCount; }
    @Override
    public String toString() {
        String feeStr = (fee != null) ? ("₹" + fee.toPlainString()) : "₹0";
        return String.format("%s — %s (%d seats) %s", 
                             (code != null ? code : "—"),
                             (title != null ? title : "Untitled"),
                             seats,
                             feeStr);
    }
}
