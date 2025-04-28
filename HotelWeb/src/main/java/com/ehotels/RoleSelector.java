package com.ehotels;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

//@WebServlet("/RoleSelector")
public class RoleSelector extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");

        if ("Customer".equals(role)) {
            request.getRequestDispatcher("/customerOptions.jsp").forward(request, response);
        } else if ("Employee".equals(role)) {
            request.getRequestDispatcher("/employeeOptions.jsp").forward(request, response);
        } else {
            response.getWriter().println("Invalid selection. Please go back and try again.");
        }
    }

    @Override
    public void init() throws ServletException {
        System.out.println("RoleSelector servlet initialized.");
    }
}
