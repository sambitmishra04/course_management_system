package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/processForm")
public class ParameterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("username");
        String[] hobbies = request.getParameterValues("hobby");

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Form Data</title></head>");
            out.println("<body>");
            out.println("<h2>Lab 6: getParameter & getParameterValues</h2>");

            out.println("<p>Name: " + (name == null ? "Not provided" : name) + "</p>");

            out.println("<p>Hobbies:</p>");
            if (hobbies == null || hobbies.length == 0) {
                out.println("<p>No hobbies selected.</p>");
            } else {
                out.println("<ul>");
                for (String h : hobbies) {
                    out.println("<li>" + h + "</li>");
                }
                out.println("</ul>");
            }

            out.println("</body></html>");
        }
    }
}
