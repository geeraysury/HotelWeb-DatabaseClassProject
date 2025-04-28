package com.ehotels;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CustomerSignupServlet")
public class CustomerSignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String typeOfID = request.getParameter("typeOfID");
        String address = request.getParameter("address");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Customer (full_name, type_of_id, registration_date, address) " +
                    "VALUES (?, ?, CURRENT_DATE, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fullName);
            stmt.setString(2, typeOfID);
            stmt.setString(3, address);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int customerId = generatedKeys.getInt(1);

                HttpSession session = request.getSession();
                session.setAttribute("customerId", customerId);

                response.sendRedirect("custHomepage.jsp");
            } else {
                throw new Exception("Customer signup failed, no ID returned.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error during signup: " + e.getMessage());
        }
    }
}
