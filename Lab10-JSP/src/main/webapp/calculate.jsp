<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="interest" class="beans.InterestBean" scope="request" />
<jsp:setProperty name="interest" property="*" />

<!DOCTYPE html>
<html>
<head>
    <title>Calculation Result</title>
</head>
<body>
    <h2>Calculation Result</h2>

    <c:if test="${interest.principal > 0}">
        <p>Principal: ${interest.principal}</p>
        <p>Rate: ${interest.rate}</p>
        <p>Time: ${interest.time}</p>
        <p><b>Simple Interest: ${interest.simpleInterest}</b></p>
    </c:if>

    <c:if test="${interest.principal <= 0}">
        <p style="color:red;">Invalid input! Please enter valid values.</p>
    </c:if>

    <br><a href="index.jsp">Back</a>
</body>
</html>
