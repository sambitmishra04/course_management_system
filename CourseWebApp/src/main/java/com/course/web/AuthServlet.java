package com.course.web;

import com.course.dao.StudentDAO;
import com.course.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("register".equals(action)) {
                Student s = new Student();
                s.setUsername(req.getParameter("username"));
                s.setPassword(req.getParameter("password")); // TODO: hash in production
                s.setFullName(req.getParameter("full_name"));
                s.setEmail(req.getParameter("email"));
                s.setPhone(req.getParameter("phone"));
                studentDAO.register(s);
                resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=registered");
            } else if ("login".equals(action)) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                Student s = studentDAO.login(username, password);
                if (s != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("student", s);
                    // optional session timeout
                    session.setMaxInactiveInterval(30*60); // 30 minutes
                    resp.sendRedirect(req.getContextPath() + "/courses");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login.jsp?error=1");
                }
            } else if ("logout".equals(action)) {
                HttpSession session = req.getSession(false);
                if (session != null) session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=loggedout");
            } else {
                resp.sendError(400, "Unknown action");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

