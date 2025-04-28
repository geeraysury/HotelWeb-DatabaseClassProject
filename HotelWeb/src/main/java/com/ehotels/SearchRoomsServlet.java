
package com.ehotels;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@WebServlet("/SearchRoomsServlet")
public class SearchRoomsServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("custHomepage.jsp");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String targetPage = request.getParameter("page");

        String hotelName = request.getParameter("hotelName");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");

        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String capacity = request.getParameter("capacity");
        String view = request.getParameter("view");
        String extendable = request.getParameter("extendable");

        List<Room> availableRooms = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String hotelIdQuery = "SELECT hotel_id FROM Hotel WHERE address ILIKE ?";
            PreparedStatement hotelStmt = conn.prepareStatement(hotelIdQuery);
            hotelStmt.setString(1, "%" + hotelName + "%");
            ResultSet hotelRs = hotelStmt.executeQuery();

            if (!hotelRs.next()) {
                request.setAttribute("noRooms", true);
                request.getRequestDispatcher("custHomepage.jsp").forward(request, response);
                return;
            }

            int hotelId = hotelRs.getInt("hotel_id");

            StringBuilder roomQuery = new StringBuilder("SELECT * FROM Room WHERE hotel_id = ?");
            if (minPrice != null && !minPrice.isEmpty()) roomQuery.append(" AND price >= ").append(minPrice);
            if (maxPrice != null && !maxPrice.isEmpty()) roomQuery.append(" AND price <= ").append(maxPrice);
            if (capacity != null && !capacity.isEmpty()) roomQuery.append(" AND capacity = ").append(capacity);
            if (view != null && !view.isEmpty()) roomQuery.append(" AND view ILIKE '").append(view).append("'");
            if (extendable != null && !extendable.isEmpty()) {
                boolean extVal = extendable.equalsIgnoreCase("true") || extendable.equalsIgnoreCase("yes");
                roomQuery.append(" AND bed_can_be_extended = ").append(extVal);
            }

            PreparedStatement roomStmt = conn.prepareStatement(roomQuery.toString());
            roomStmt.setInt(1, hotelId);
            ResultSet roomRs = roomStmt.executeQuery();

            while (roomRs.next()) {
                int roomNumber = roomRs.getInt("room_number");


                String checkQuery = "SELECT 1 FROM Stay WHERE room_number = ? AND hotel_id = ? " +
                        "AND NOT (check_out_date <= ? OR check_in_date >= ?)";

                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, roomNumber);
                checkStmt.setInt(2, hotelId);
                checkStmt.setDate(3, Date.valueOf(checkIn));
                checkStmt.setDate(4, Date.valueOf(checkOut));
                ResultSet conflictRs = checkStmt.executeQuery();

                if (!conflictRs.next()) {
                    Room room = new Room();
                    room.setRoomNumber(roomNumber);
                    room.setHotelId(hotelId);
                    room.setPrice(roomRs.getDouble("price"));
                    room.setCapacity(roomRs.getInt("capacity"));
                    room.setView(roomRs.getString("view"));
                    room.setExtendable(roomRs.getBoolean("bed_can_be_extended"));
                    availableRooms.add(room);

                    String amenityQuery = "SELECT A.name FROM Amenities A JOIN Features F ON A.amenity_id = F.amenity_id WHERE F.room_number = ? AND F.hotel_id = ?";
                    PreparedStatement amenityStmt = conn.prepareStatement(amenityQuery);
                    amenityStmt.setInt(1, roomNumber);
                    amenityStmt.setInt(2, hotelId);
                    ResultSet amenityRs = amenityStmt.executeQuery();

                    List<String> amenities = new ArrayList<>();
                    while (amenityRs.next()) {
                        amenities.add(amenityRs.getString("name"));
                    }
                    room.setAmenities(amenities);
                }

            }

            if (availableRooms.isEmpty()) {
                request.setAttribute("noRooms", true);
            } else {
                request.setAttribute("rooms", availableRooms);
            }

            request.getRequestDispatcher(targetPage).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
