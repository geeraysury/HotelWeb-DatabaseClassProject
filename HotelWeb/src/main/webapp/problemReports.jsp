<%--
  Created by IntelliJ IDEA.
  User: onyo
  Date: 2025-03-28
  Time: 8:23 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ehotels.ProblemReport" %>
<!DOCTYPE html>
<html>
<head>
  <title>Room Problem Reports</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
    }
    .problem-container {
      margin-bottom: 30px;
      border: 1px solid #ddd;
      padding: 15px;
      border-radius: 5px;
    }
    .problem-card {
      background-color: #f9f9f9;
      border: 1px solid #ccc;
      border-radius: 5px;
      padding: 15px;
      margin-bottom: 15px;
    }
    .form-group {
      margin-bottom: 15px;
    }
    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    input, select, textarea {
      width: 100%;
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    button {
      background-color: #4CAF50;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
    .status-pending { color: #e67e22; }
    .status-in-progress { color: #3498db; }
    .status-resolved { color: #2ecc71; }
  </style>
</head>
<body>

<h2>Room Problem Reports</h2>

<% String successMessage = (String) request.getAttribute("successMessage");
  String errorMessage = (String) request.getAttribute("errorMessage");
  if (successMessage != null) { %>
<p style="color: green;"><%= successMessage %></p>
<% } else if (errorMessage != null) { %>
<p style="color: red;"><%= errorMessage %></p>
<% } %>

<div class="problem-container">
  <h3>Report New Problem</h3>
  <form action="ProblemReportServlet" method="post">
    <input type="hidden" name="action" value="create">
    <div class="form-group">
      <label for="roomNumber">Room Number:</label>
      <input type="number" id="roomNumber" name="roomNumber" required>
    </div>
    <div class="form-group">
      <label for="description">Problem Description:</label>
      <textarea id="description" name="description" rows="4" required></textarea>
    </div>
    <button type="submit">Submit Report</button>
  </form>
</div>


<div class="problem-container">
  <h3>Existing Problems</h3>
  <%
    List<ProblemReport> problems = (List<ProblemReport>) request.getAttribute("problems");
    if (problems != null && !problems.isEmpty()) {
      for (ProblemReport problem : problems) {
        String statusClass = problem.getStatus().toLowerCase().replace(" ", "-").trim();
  %>
  <div class="problem-card">
    <p><strong>Room #<%= problem.getRoomNumber() %></strong> at <strong><%= problem.getHotelName() %></strong></p>
    <p><strong>Reported:</strong> <%= problem.getReportedDate() %></p>
    <p><strong>Status:</strong>
      <span class="status-<%= statusClass %>">
                <%= problem.getStatus() %>
            </span>
    </p>
    <p><strong>Description:</strong> <%= problem.getDescription() %></p>
    <% if (problem.getFixedDate() != null) { %>
    <p><strong>Fixed on:</strong> <%= problem.getFixedDate() %></p>
    <% } %>

    <!-- Update form for employees -->
    <form action="ProblemReportServlet" method="post" style="margin-top: 10px;">
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="problemId" value="<%= problem.getProblemId() %>">
      <div class="form-group">
        <label for="status-<%= problem.getProblemId() %>">Update Status:</label>
        <select id="status-<%= problem.getProblemId() %>" name="status">
          <option value="pending" <%= "pending".equalsIgnoreCase(problem.getStatus()) ? "selected" : "" %>>Pending</option>
          <option value="in progress" <%= "in progress".equalsIgnoreCase(problem.getStatus()) ? "selected" : "" %>>In Progress</option>
          <option value="resolved" <%= "resolved".equalsIgnoreCase(problem.getStatus()) ? "selected" : "" %>>Resolved</option>
        </select>
      </div>
      <button type="submit">Update Problem</button>
    </form>
  </div>
  <%
    }
  } else {
  %>
  <p>No problems reported yet.</p>
  <% } %>
</div>

<a href="empHomepage.jsp">Back to Employee Homepage</a>

</body>
</html>
