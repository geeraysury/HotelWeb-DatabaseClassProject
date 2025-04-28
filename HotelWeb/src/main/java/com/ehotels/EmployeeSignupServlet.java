package com.ehotels;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/EmployeeSignupServlet")
public class EmployeeSignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String ssnSin = request.getParameter("ssnSin");
        String hotelName = request.getParameter("hotelName");

        try (Connection conn = DatabaseConnection.getConnection()) {

            String hotelSql = "SELECT hotel_id FROM Hotel WHERE address ILIKE ?";
            PreparedStatement hotelStmt = conn.prepareStatement(hotelSql);
            hotelStmt.setString(1, "%" + hotelName + "%");
            ResultSet rs = hotelStmt.executeQuery();

            if (rs.next()) {
                int hotelId = rs.getInt("hotel_id");

                String insertSql = "INSERT INTO Employee (full_name, address, SSN_SIN, hotel_id) " +
                        "VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, fullName);
                insertStmt.setString(2, address);
                insertStmt.setString(3, ssnSin);
                insertStmt.setInt(4, hotelId);
                insertStmt.executeUpdate();

                response.sendRedirect("empHomepage.jsp");
            } else {
                response.getWriter().println("Hotel not found in system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error during signup: " + e.getMessage());
        }
    }
}
