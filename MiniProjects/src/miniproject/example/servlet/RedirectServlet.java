package com.example.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/goSite")
public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String site = request.getParameter("site");

        if (site == null || site.isBlank()) {
            // default redirect
            response.sendRedirect("https://www.google.com");
        } else {
            if (!site.startsWith("http://") && !site.startsWith("https://")) {
                site = "https://" + site;
            }
            response.sendRedirect(site);
        }
    }
}
