package com.ehotels;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String typeOfID = request.getParameter("typeOfID");
        String address = request.getParameter("address");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE full_name = ? AND type_of_id = ? AND address = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, typeOfID);
            stmt.setString(3, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("customer_id");
                HttpSession session = request.getSession();
                session.setAttribute("customerId", customerId);
                response.sendRedirect("custHomepage.jsp");

            } else {
                request.setAttribute("error", "Incorrect login information.");
                request.getRequestDispatcher("custLogin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}

