<%--
  Created by IntelliJ IDEA.
  User: onyo
  Date: 2025-03-25
  Time: 8:16 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Customer Login</title></head>
<body>
<h2>Customer Login</h2>

<form action="CustomerLoginServlet" method="post">
    <label>Full Name:</label><br>
    <input type="text" name="fullName" required><br><br>

    <label>Type of ID:</label><br>
    <input type="text" name="typeOfID" required><br><br>

    <label>Address:</label><br>
    <input type="text" name="address" required><br><br>

    <button type="submit">Log In</button>
</form>

<% if (request.getAttribute("error") != null) { %>
<p style="color: red;"><%= request.getAttribute("error") %></p>
<% } %>

<br><a href="index.jsp">← Back to Home</a>
</body>
</html>

