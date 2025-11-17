package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/formHandler")   // URL pattern for your form’s action
public class FormHandlerServlet extends HttpServlet {
	
	
	  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            // Fetch form data
            String name = request.getParameter("username");
            String email = request.getParameter("email");
            String gender = request.getParameter("gender");

            // Display response
            out.println("<html><body>");
            out.println("<h2>Form Submitted Successfully!</h2>");
            out.println("<p><b>Name:</b> " + name + "</p>");
            out.println("<p><b>Email:</b> " + email + "</p>");
            out.println("<p><b>Gender:</b> " + gender + "</p>");
            out.println("</body></html>");
        }
    }
}
