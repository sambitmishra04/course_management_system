package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//url: http://localhost:8080/Lab9/greet?name=Coding&age=20
@WebServlet("/greet")
public class DynamicContentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String age = request.getParameter("age");

        if (name == null || name.isBlank()) {
            name = "Guest";
        }
        if (age == null || age.isBlank()) {
            age = "unknown";
        }

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Dynamic Content</title></head>");
            out.println("<body>");
            out.println("<h2>Lab 4: Dynamic Content via Request Parameters</h2>");
            out.println("<p>Hello, " + name + "!</p>");
            out.println("<p>Your age is: " + age + "</p>");
            out.println("</body></html>");
        }
    }
}
