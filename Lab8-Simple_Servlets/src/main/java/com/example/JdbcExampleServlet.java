package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/jdbcExample")
public class JdbcExampleServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "MySQL@2024";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

                out.println("<html><body>");
                out.println("<h2>User List from Database</h2>");
                out.println("<ul>");
                while (rs.next()) {
                    out.println("<li>" + rs.getInt("id") + ": " + rs.getString("name") +
                                " (" + rs.getString("email") + ")</li>");
                }
                out.println("</ul>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
