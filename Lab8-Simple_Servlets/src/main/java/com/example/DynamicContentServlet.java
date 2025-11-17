package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dynamicContent")
public class DynamicContentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String color = request.getParameter("color");

        // Default values if parameters are missing
        if (username == null || username.isBlank()) username = "Guest";
        if (color == null || color.isBlank()) color = "blue";

        LocalTime time = LocalTime.now();
        String greeting;
        if (time.isBefore(LocalTime.NOON)) {
            greeting = "Good Morning";
        } else if (time.isBefore(LocalTime.of(17, 0))) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body style='background-color:" + color.toLowerCase() + "; color:white; text-align:center;'>");
            out.println("<h2>" + greeting + ", " + username + "!</h2>");
            out.println("<p>The background color is set dynamically to your choice: <b>" + color + "</b></p>");
            out.println("<p>Current Server Time: " + time + "</p>");
            out.println("</body></html>");
        }
    }
}
