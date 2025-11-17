package com.example.servlet;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forwardSource")
public class ForwardSourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            name = "Guest";
        }

        // Add attribute to request scope
        request.setAttribute("userName", name);

        // Forward to another servlet
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("forwardTarget");
        dispatcher.forward(request, response);
    }
}
