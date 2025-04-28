package com.ehotels;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ProblemReportServlet")
public class ProblemReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            response.sendRedirect("employeeLogin.jsp");
            return;
        }

        int employeeId = (int) session.getAttribute("employeeId");

        try (Connection conn = DatabaseConnection.getConnection()) {

            String hotelSql = "SELECT e.hotel_id, h.address AS hotel_name FROM Employee e " +
                    "JOIN Hotel h ON e.hotel_id = h.hotel_id " +
                    "WHERE e.employee_id = ?";
            PreparedStatement hotelStmt = conn.prepareStatement(hotelSql);
            hotelStmt.setInt(1, employeeId);
            ResultSet hotelRs = hotelStmt.executeQuery();

            if (!hotelRs.next()) {
                throw new SQLException("Employee not associated with any hotel.");
            }

            int hotelId = hotelRs.getInt("hotel_id");
            String hotelName = hotelRs.getString("hotel_name");

            String sql = "SELECT * FROM Problems WHERE hotel_id = ? ORDER BY reported_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            List<ProblemReport> problems = new ArrayList<>();
            while (rs.next()) {
                ProblemReport problem = new ProblemReport(
                        rs.getInt("problem_id"),
                        rs.getInt("room_number"),
                        rs.getInt("hotel_id"),
                        rs.getString("description"),
                        rs.getDate("reported_date"),
                        rs.getDate("fixed_date"),
                        rs.getString("status")
                );
                problem.setHotelName(hotelName);
                problems.add(problem);
            }

            request.setAttribute("problems", problems);
            request.getRequestDispatcher("problemReports.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving problems: " + e.getMessage());
            request.getRequestDispatcher("problemReports.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("employeeId") == null) {
            response.sendRedirect("employeeLogin.jsp");
            return;
        }

        int employeeId = (int) session.getAttribute("employeeId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            String hotelSql = "SELECT hotel_id FROM Employee WHERE employee_id = ?";
            PreparedStatement hotelStmt = conn.prepareStatement(hotelSql);
            hotelStmt.setInt(1, employeeId);
            ResultSet hotelRs = hotelStmt.executeQuery();

            if (!hotelRs.next()) {
                throw new SQLException("Employee not found.");
            }
            int hotelId = hotelRs.getInt("hotel_id");

            if ("create".equals(action)) {
                int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
                String description = request.getParameter("description");

                String roomCheckSql = "SELECT 1 FROM Room WHERE room_number = ? AND hotel_id = ?";
                PreparedStatement roomCheckStmt = conn.prepareStatement(roomCheckSql);
                roomCheckStmt.setInt(1, roomNumber);
                roomCheckStmt.setInt(2, hotelId);
                ResultSet roomCheckRs = roomCheckStmt.executeQuery();

                if (!roomCheckRs.next()) {
                    request.setAttribute("errorMessage", "Room " + roomNumber + " does not exist in your hotel.");
                    doGet(request, response);
                    return;
                }

                String sql = "INSERT INTO Problems (room_number, hotel_id, description, reported_date, status) " +
                        "VALUES (?, ?, ?, CURRENT_DATE, 'pending')";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, roomNumber);
                stmt.setInt(2, hotelId);
                stmt.setString(3, description);
                stmt.executeUpdate();

                conn.commit();
                request.setAttribute("successMessage", "Problem reported successfully!");

            } else if ("update".equals(action)) {
                int problemId = Integer.parseInt(request.getParameter("problemId"));
                String status = request.getParameter("status");


                String sql = "UPDATE Problems SET status = ?, " +
                        "fixed_date = CASE WHEN ? = 'resolved' THEN CURRENT_DATE ELSE NULL END " +
                        "WHERE problem_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, status);
                stmt.setString(2, status);
                stmt.setInt(3, problemId);
                stmt.executeUpdate();

                conn.commit();
                request.setAttribute("successMessage", "Problem updated successfully!");
            }

            doGet(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing problem report: " + e.getMessage());
            request.getRequestDispatcher("problemReports.jsp").forward(request, response);
        }
    }
}