package com.ehotels;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@WebServlet("/BookRoomServlet")
public class BookRoomServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("custHomepage.jsp");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        Date checkIn = Date.valueOf(request.getParameter("checkIn"));
        Date checkOut = Date.valueOf(request.getParameter("checkOut"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect("custLogin.jsp");
            return;
        }

        int customerId = (int) session.getAttribute("customerId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String staySql = "INSERT INTO Stay (customer_id, room_number, hotel_id, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, ?, 'active')";
            PreparedStatement stayStmt = conn.prepareStatement(staySql, Statement.RETURN_GENERATED_KEYS);
            stayStmt.setInt(1, customerId);
            stayStmt.setInt(2, roomNumber);
            stayStmt.setInt(3, hotelId);
            stayStmt.setDate(4, checkIn);
            stayStmt.setDate(5, checkOut);
            stayStmt.executeUpdate();

            ResultSet rs = stayStmt.getGeneratedKeys();
            if (rs.next()) {
                int stayId = rs.getInt(1);


                String bookSql = "INSERT INTO Book (stay_id, booking_date) VALUES (?, CURRENT_DATE)";
                PreparedStatement bookStmt = conn.prepareStatement(bookSql);
                bookStmt.setInt(1, stayId);
                bookStmt.executeUpdate();
            }


            session.setAttribute("bookingSuccess", "1");


            response.sendRedirect("SearchRoomsServlet");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Booking failed: " + e.getMessage());
        }
    }
}
