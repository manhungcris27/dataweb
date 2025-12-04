<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanks</title>
</head>
<body>

<h2>Thanks for joining our email list</h2>

<p>Here is the information that you entered:</p>

<p><strong>Email:</strong> ${user.email}</p>
<p><strong>First Name:</strong> ${user.firstName}</p>
<p><strong>Last Name:</strong> ${user.lastName}</p>

<p>To enter another email address, click on the Back button in your 
browser or the Return button shown below.</p>

<form action="index.jsp" method="get">
    <input type="submit" value="Return">
</form>

</body>
</html>
