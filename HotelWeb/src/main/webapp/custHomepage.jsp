<%--
  Created by IntelliJ IDEA.
  User: onyo
  Date: 2025-03-25
  Time: 8:37 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Room Search</title>

    <script>
        function displayHotelName() {
            const name = document.getElementById("hotelName").value;
            document.getElementById("hotelDisplay").innerHTML = "<h1>" + name + "</h1>";
        }

        function showAmenities(id) {
            const popup = document.getElementById("popup_" + id);
            popup.style.display = "block";

            const resultsContainer = document.querySelector('.results');
            const containerRect = resultsContainer.getBoundingClientRect();


            popup.style.top = (resultsContainer.scrollTop + containerRect.height / 2) + 'px';
            popup.style.left = '50%';
        }

        function hideAmenities(id) {
            document.getElementById("popup_" + id).style.display = "none";
        }

        function closeBookingPopup() {
            document.getElementById("bookingPopup").style.display = "none";
        }


        window.onload = function() {
            <% if (request.getAttribute("bookingSuccess") != null && request.getAttribute("bookingSuccess").equals("1")) { %>
            document.getElementById("bookingPopup").style.display = "block";
            <% } %>
        };
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        #bookingPopup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1001;
            background: #fff;
            border: 2px solid #4CAF50;
            border-radius: 12px;
            padding: 30px 40px;
            width: 400px;
            max-width: 90%;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            text-align: center;
        }

        #bookingPopup h3 {
            color: #4CAF50;
            margin-top: 0;
        }

        #bookingPopup button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 15px;
        }

        #bookingPopup button:hover {
            background-color: #45a049;
        }
        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1000;
            background: #fff;
            border: 2px solid #4CAF50;
            border-radius: 12px;
            padding: 30px 40px;
            width: 400px;
            max-width: 90%;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        }

        .popup p {
            margin: 0;
            padding: 10px 0;
            text-align: center;
        }

        .popup button {
            display: block;
            margin: 10px auto 0;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .popup button:hover {
            background-color: #45a049;
        }


        .search-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
        }


        .search-bar input[type="text"],
        .search-bar input[type="date"] {
            padding: 5px;
            font-size: 14px;
        }

        .search-bar button {
            padding: 8px 16px;
            font-size: 14px;
            cursor: pointer;
        }

        .filters {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 6px;
            max-width: 800px;
            background-color: #f9f9f9;
        }

        .filters label {
            display: block;
            margin-top: 10px;
        }

        .filters input, .filters select {
            margin-top: 5px;
            padding: 5px;
            width: 100%;
            max-width: 300px;
        }

        .results {
            position: relative;
            margin-top: 20px;
            max-height: 400px;
            overflow-y: auto;
            padding-right: 10px;
        }


        .room-card {
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

    </style>
</head>
<body>

<div id="bookingPopup">
    <h3>Booking Successful!</h3>
    <p>Your room has been successfully booked.</p>
    <button onclick="closeBookingPopup()">OK</button>
</div>

<h2>Find Your Perfect Room</h2>

<form action="SearchRoomsServlet" method="post">
    <input type="hidden" name="page" value="custHomepage.jsp" />
    <div class="search-bar">
        <input type="text" name="hotelName" placeholder="Search by hotel name..." required />
        <input type="date" name="checkIn" required />
        <input type="date" name="checkOut" required />
        <button type="submit">Search</button>
    </div>


    <div class="filters">
        <label>
            Price Range:
            <div style="display: flex; gap: 10px;">
                <input type="number" name="minPrice" placeholder="Min ($)">
                <input type="number" name="maxPrice" placeholder="Max ($)">
            </div>
        </label>

        <label>
            Capacity:
            <input type="number" name="capacity" placeholder="Enter number of guests">
        </label>

        <label>
            View:
            <select name="view">
                <option value="">Any</option>
                <option value="city">City</option>
                <option value="garden">Garden</option>
                <option value="sea">Sea</option>
                <option value="mountain">Mountain</option>
            </select>
        </label>

        <label>
            Bed Can Be Extended:
            <select name="extendable">
                <option value="">Any</option>
                <option value="true">Yes</option>
                <option value="false">No</option>
            </select>
        </label>
    </div>


    <div id="hotelDisplay" style="margin: 15px 0; font-weight: bold;"></div>

</form>

<div class="results">
    <% if (request.getAttribute("noRooms") != null) { %>
    <p>No rooms left for your search.</p>
    <% } else if (request.getAttribute("rooms") != null) {
        java.util.List<com.ehotels.Room> rooms = (java.util.List<com.ehotels.Room>) request.getAttribute("rooms");
        for (com.ehotels.Room room : rooms) { %>

    <div class="room-card">
        <p><strong>Room Number:</strong> <%= room.getRoomNumber() %></p>
        <p><strong>Price:</strong> $<%= room.getPrice() %></p>
        <p><strong>Capacity:</strong> <%= room.getCapacity() %></p>
        <p><strong>View:</strong> <%= room.getView() %></p>
        <p><strong>Bed Can Be Extended:</strong> <%= room.isExtendable() ? "Yes" : "No" %></p>

        <button onclick="showAmenities('<%= room.getRoomNumber() %>_<%= room.getHotelId() %>')">View Amenities</button>
        <form action="BookRoomServlet" method="post" style="text-align: right; margin-top: 10px;">
            <input type="hidden" name="roomNumber" value="<%= room.getRoomNumber() %>">
            <input type="hidden" name="hotelId" value="<%= room.getHotelId() %>">
            <input type="hidden" name="checkIn" value="<%= request.getParameter("checkIn") %>">
            <input type="hidden" name="checkOut" value="<%= request.getParameter("checkOut") %>">
            <input type="hidden" name="hotelName" value="<%= request.getParameter("hotelName") %>">
            <input type="hidden" name="minPrice" value="<%= request.getParameter("minPrice") %>">
            <input type="hidden" name="maxPrice" value="<%= request.getParameter("maxPrice") %>">
            <input type="hidden" name="capacity" value="<%= request.getParameter("capacity") %>">
            <input type="hidden" name="view" value="<%= request.getParameter("view") %>">
            <input type="hidden" name="extendable" value="<%= request.getParameter("extendable") %>">
            <button type="submit">Book</button>
        </form>

    </div>

    <div id="popup_<%= room.getRoomNumber() %>_<%= room.getHotelId() %>" class="popup">
        <h3>Amenities</h3>
        <ul>
            <% for (String amenity : room.getAmenities()) { %>
            <li><%= amenity %></li>
            <% } %>
        </ul>
        <button onclick="hideAmenities('<%= room.getRoomNumber() %>_<%= room.getHotelId() %>')">Close</button>
    </div>

    <% } } %>
</div>




<form action="index.jsp" method="get" style="text-align: right; margin-bottom: 10px;">
    <button type="submit">Logout</button>
</form>

</body>


</html>
