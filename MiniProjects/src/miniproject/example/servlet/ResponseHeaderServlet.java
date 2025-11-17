package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/headersDemo")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // MIME type: HTML
        response.setContentType("text/html;charset=UTF-8");

        // Set some response headers
        response.setHeader("Refresh", "5"); // refresh page every 5 seconds
        response.setHeader("X-Custom-Header", "Lab5Demo");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Response Headers</title></head>");
            out.println("<body>");
            out.println("<h2>Lab 5: Response Headers and MIME Types</h2>");
            out.println("<p>Current time: " + LocalDateTime.now() + "</p>");
            out.println("<p>Page will auto-refresh every 5 seconds.</p>");
            out.println("</body></html>");
        }
    }
}
