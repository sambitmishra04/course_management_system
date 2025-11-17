<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Interest Calculator</title>
</head>
<body>
    <h2>Simple Interest Calculator</h2>
    <form action="calculate.jsp" method="post">
        Principal: <input type="text" name="principal"><br><br>
        Rate: <input type="text" name="rate"><br><br>
        Time: <input type="text" name="time"><br><br>
        <input type="submit" value="Calculate">
    </form>
</body>
</html>
