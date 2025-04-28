package com.ehotels;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewBookingsServlet")
public class ViewBookingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            response.sendRedirect("employeeLogin.jsp");
            return;
        }

        int employeeId = (int) session.getAttribute("employeeId");

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT b.booking_id, s.room_number, s.hotel_id, h.address, c.full_name, " +
                    "s.check_in_date, s.check_out_date " +
                    "FROM Book b " +
                    "JOIN Stay s ON b.stay_id = s.stay_id " +
                    "JOIN Customer c ON s.customer_id = c.customer_id " +
                    "JOIN Hotel h ON s.hotel_id = h.hotel_id " +
                    "WHERE s.status = 'active' " +
                    "AND s.hotel_id IN (SELECT hotel_id FROM Employee WHERE employee_id = ?) " +
                    "AND NOT EXISTS (SELECT 1 FROM Rent r WHERE r.booking_id = b.booking_id) " +
                    "ORDER BY s.check_in_date DESC";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            List<BookingRecord> bookings = new ArrayList<>();
            while (rs.next()) {
                BookingRecord booking = new BookingRecord(
                        rs.getInt("booking_id"),
                        rs.getInt("room_number"),
                        rs.getInt("hotel_id"),
                        rs.getString("address"),
                        rs.getString("full_name"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date")
                );
                bookings.add(booking);
            }

            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("bookingRecords.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving booking records: " + e.getMessage());
            request.getRequestDispatcher("empHomepage.jsp").forward(request, response);
        }
    }
}
