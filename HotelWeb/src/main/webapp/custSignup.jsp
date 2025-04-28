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
    <title>Customer Sign Up</title>
    <script>
        function validateCustomerForm() {
            const fullName = document.getElementById("fullName").value.trim();
            const typeOfID = document.getElementById("typeOfID").value.trim();
            const address = document.getElementById("address").value.trim();

            if (!fullName || !typeOfID || !address) {
                alert("Please fill out all fields before signing up.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<h2>Customer Sign Up</h2>
<form action="CustomerSignupServlet" method="post" onsubmit="return validateCustomerForm();">
    <label>Full Name:</label><br>
    <input type="text" id="fullName" name="fullName"><br><br>

    <label>Type of ID:</label><br>
    <input type="text" id="typeOfID" name="typeOfID"><br><br>

    <label>Address:</label><br>
    <input type="text" id="address" name="address"><br><br>

    <button type="submit">Sign Up</button>

</form>
</body>
</html>

