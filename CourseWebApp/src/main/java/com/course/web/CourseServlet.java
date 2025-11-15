package com.course.web;

import com.course.dao.CourseDAO;
import com.course.dao.EnrollmentDAO;
import com.course.model.Course;
import com.course.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    private CourseDAO courseDAO = new CourseDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Course> list = courseDAO.getAll();
            req.setAttribute("courses", list);

            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("student") != null) {
                Student s = (Student) session.getAttribute("student");
                Set<Integer> enrolled = enrollmentDAO.getEnrolledCourseIds(s.getStudentId());
                req.setAttribute("enrolledCourseIds", enrolled);
            }

            req.getRequestDispatcher("/WEB-INF/views/courses.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

