<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome - Select User Type</title>
</head>
<body>
<h2>Welcome! Please select your user type:</h2>

<form action="${pageContext.request.contextPath}/RoleSelector" method="post">
    <input type="submit" name="role" value="Customer" />
    <input type="submit" name="role" value="Employee" />
</form>
</body>
</html>
