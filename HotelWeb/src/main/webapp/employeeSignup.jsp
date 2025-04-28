<%--
  Created by IntelliJ IDEA.
  User: onyo
  Date: 2025-03-23
  Time: 10:56 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Sign Up</title>
    <script>
        function validateEmployeeForm() {
            const fullName = document.getElementById("fullName").value.trim();
            const address = document.getElementById("address").value.trim();
            const ssnSin = document.getElementById("ssnSin").value.trim();
            const hotelName = document.getElementById("hotelName").value.trim();

            if (!fullName || !address || !ssnSin || !hotelName) {
                alert("Please fill out all fields before signing up.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<h2>Employee Sign Up</h2>
<form action="EmployeeSignupServlet" method="post" onsubmit="return validateEmployeeForm();">
    <label>Full Name:</label><br>
    <input type="text" id="fullName" name="fullName"><br><br>

    <label>Address:</label><br>
    <input type="text" id="address" name="address"><br><br>

    <label>SSN/SIN:</label><br>
    <input type="text" id="ssnSin" name="ssnSin"><br><br>

    <label>Hotel Name:</label><br>
    <input type="text" id="hotelName" name="hotelName"><br><br>

    <button type="submit">Sign Up</button>

</form>
</body>
</html>

