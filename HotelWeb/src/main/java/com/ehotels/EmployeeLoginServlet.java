package com.ehotels;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/EmployeeLoginServlet")
public class EmployeeLoginServlet extends HttpServlet {
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
            ResultSet hotelRs = hotelStmt.executeQuery();

            if (hotelRs.next()) {
                int hotelId = hotelRs.getInt("hotel_id");

                String sql = "SELECT * FROM Employee WHERE full_name = ? AND address = ? AND ssn_sin = ? AND hotel_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, fullName);
                stmt.setString(2, address);
                stmt.setString(3, ssnSin);
                stmt.setInt(4, hotelId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int employeeId = rs.getInt("employee_id");
                    HttpSession session = request.getSession();
                    session.setAttribute("employeeId", employeeId);
                    response.sendRedirect("empHomepage.jsp");
                } else {
                    request.setAttribute("error", "Incorrect login information.");
                    request.getRequestDispatcher("employeeLogin.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Hotel not found.");
                request.getRequestDispatcher("employeeLogin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
