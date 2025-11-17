package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forwardTarget")
public class ForwardTargetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String name = (String) request.getAttribute("userName");

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Forward Target</title></head>");
            out.println("<body>");
            out.println("<h2>Lab 8: RequestDispatcher Forward</h2>");
            out.println("<p>Request forwarded successfully.</p>");
            out.println("<p>Hello, " + name + "!</p>");
            out.println("</body></html>");
        }
    }
}
