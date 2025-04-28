package com.ehotels;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RentRoomServlet")
public class RentRoomServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        Date checkIn = Date.valueOf(request.getParameter("checkIn"));
        Date checkOut = Date.valueOf(request.getParameter("checkOut"));
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            response.sendRedirect("employeeLogin.jsp");
            return;
        }

        int employeeId = (int) session.getAttribute("employeeId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction


            String staySql = "SELECT stay_id FROM Book WHERE booking_id = ?";
            PreparedStatement stayStmt = conn.prepareStatement(staySql);
            stayStmt.setInt(1, bookingId);
            ResultSet stayRs = stayStmt.executeQuery();

            if (!stayRs.next()) {
                throw new SQLException("Booking not found for booking_id: " + bookingId);
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

            request.setAttribute("rentSuccess", "1");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Renting failed: " + e.getMessage());
        }


        request.getRequestDispatcher("ViewBookingsServlet").forward(request, response);
    }
}
