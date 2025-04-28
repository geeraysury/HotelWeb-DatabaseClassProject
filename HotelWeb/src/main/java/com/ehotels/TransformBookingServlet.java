package com.ehotels;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/TransformBookingServlet")
public class TransformBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            response.sendRedirect("employeeLogin.jsp");
            return;
        }

        int employeeId = (int) session.getAttribute("employeeId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);


            String staySql = "SELECT stay_id FROM Book WHERE booking_id = ?";
            PreparedStatement stayStmt = conn.prepareStatement(staySql);
            stayStmt.setInt(1, bookingId);
            ResultSet stayRs = stayStmt.executeQuery();

            if (!stayRs.next()) {
                throw new SQLException("Booking not found");
            }
            int stayId = stayRs.getInt("stay_id");

            String rentSql = "INSERT INTO Rent (stay_id, booking_id, employee_id, payment_status) " +
                    "VALUES (?, ?, ?, 'paid')";
            PreparedStatement rentStmt = conn.prepareStatement(rentSql);
            rentStmt.setInt(1, stayId);
            rentStmt.setInt(2, bookingId);
            rentStmt.setInt(3, employeeId);
            rentStmt.executeUpdate();

            String deleteSql = "DELETE FROM Book WHERE booking_id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, bookingId);
            deleteStmt.executeUpdate();

            conn.commit();


            session.setAttribute("successMessage", "Booking successfully transformed to rental");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error transforming booking: " + e.getMessage());
        }


        response.sendRedirect("ViewBookingsServlet");
    }
}