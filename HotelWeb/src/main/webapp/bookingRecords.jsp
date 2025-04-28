<%--
  Created by IntelliJ IDEA.
  User: onyo
  Date: 2025-03-28
  Time: 9:37 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ehotels.BookingRecord" %>
<!DOCTYPE html>
<html>
<head>
  <title>Booking Records</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    .record-container { margin-bottom: 30px; border: 1px solid #ddd; padding: 15px; border-radius: 5px; }
    .record-card { background-color: #f9f9f9; border: 1px solid #ccc; border-radius: 5px; padding: 15px; margin-bottom: 15px; }
    button { background-color: #4CAF50; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; }
    button:hover { background-color: #45a049; }
    a { text-decoration: none; }
  </style>
</head>
<body>
<% String successMessage = (String) session.getAttribute("successMessage");
  String errorMessage = (String) request.getAttribute("errorMessage");

  if (successMessage != null) { %>
<div style="color: green; margin-bottom: 15px;"><%= successMessage %></div>
<% session.removeAttribute("successMessage"); %>
<% }
  if (errorMessage != null) { %>
<div style="color: red; margin-bottom: 15px;"><%= errorMessage %></div>
<% } %>

<h2>Booking Records</h2>

<div class="record-container">
  <%
    List<BookingRecord> bookings = (List<BookingRecord>) request.getAttribute("bookings");
    if (bookings != null && !bookings.isEmpty()) {
      for (BookingRecord booking : bookings) {
  %>
  <div class="record-card">
    <p><strong>Room #<%= booking.getRoomNumber() %></strong> at <strong><%= booking.getAddress() %></strong></p>
    <p><strong>Customer:</strong> <%= booking.getCustomerName() %></p>
    <p><strong>Check-in:</strong> <%= booking.getCheckInDate() %></p>
    <p><strong>Check-out:</strong> <%= booking.getCheckOutDate() %></p>

    <!-- Replace the existing form with this one -->
    <form action="TransformBookingServlet" method="post">
      <input type="hidden" name="bookingId" value="<%= booking.getBookingId() %>">
      <button type="submit">Transform to Rental</button>
    </form>

  </div>
  <%
    }
  } else {
  %>
  <p>No booking records available.</p>
  <% } %>
</div>

<a href="empHomepage.jsp">Back to Employee Homepage</a>

</body>
</html>
