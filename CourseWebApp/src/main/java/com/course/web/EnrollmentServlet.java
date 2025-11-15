package com.course.web;

import com.course.dao.EnrollmentDAO;
import com.course.model.EnrollmentDTO;
import com.course.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/enroll", "/my-enrollments"})
public class EnrollmentServlet extends HttpServlet {
    private EnrollmentDAO dao = new EnrollmentDAO();

    // existing POST handler for enroll / deenroll (keep as you already have)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=login_required");
            return;
        }

        Student student = (Student) session.getAttribute("student");
        String action = req.getParameter("action");
        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter("courseId"));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/courses?error=invalid_course");
            return;
        }

        try {
            if ("enroll".equals(action)) {
                boolean ok = dao.enroll(student.getStudentId(), courseId);
                if (ok) resp.sendRedirect(req.getContextPath() + "/courses?msg=enrolled");
                else resp.sendRedirect(req.getContextPath() + "/courses?error=enroll_failed");
            } else if ("deenroll".equals(action)) {
                boolean ok = dao.deEnroll(student.getStudentId(), courseId);
                if (ok) resp.sendRedirect(req.getContextPath() + "/courses?msg=de_enrolled");
                else resp.sendRedirect(req.getContextPath() + "/courses?error=de_enroll_failed");
            } else {
                resp.sendError(400, "Unknown action");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // new GET handler: show logged-in student's enrollments
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // if request path is /my-enrollments, show enrollments; otherwise forward to courses (optional)
        String path = req.getServletPath();
        if ("/my-enrollments".equals(path)) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("student") == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=login_required");
                return;
            }
            Student student = (Student) session.getAttribute("student");
            try {
                List<EnrollmentDTO> enrollments = dao.getEnrollmentsByStudent(student.getStudentId());
                req.setAttribute("enrollments", enrollments);
                req.getRequestDispatcher("/WEB-INF/views/enrollments.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/courses");
        }
    }
}
