<%--
  Created by IntelliJ IDEA.
  User: roma
  Date: 2020-04-28
  Time: 12:54 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../jsp/header.jsp" %>
<html>
<head>
    <title>ErrorPage</title>
</head>
<body style="background-image: url('/view/images/background.jpg')">
<div class="container text-center text-white">
    <c:choose>
        <c:when test="${not empty errorLogin}">
            <h1>${errorLogin}</h1>
        </c:when>
        <c:when test="${not empty errorBlocked}">
            <h1>${errorBlocked}</h1>
        </c:when>
        <c:when test="${not empty errorUser}">
            <h1>${errorUser}</h1>
        </c:when>
        <c:when test="${not empty errorProduct}">
            <h1>${errorProduct}</h1>
        </c:when>
        <c:when test="${not empty access}">
            <h1>${access}</h1>
        </c:when>
        <c:when test="${requestScope['javax.servlet.error.status_code'] == 404}">
            <h1>404<br>
                NOT FOUND
            </h1>
        </c:when>
        <c:when test="${requestScope['javax.servlet.error.status_code'] == 500}">
            <h1>Something was wrong, back and try again =)</h1>
        </c:when>
    </c:choose>
    <form action="/GetAllProducts">
        <input type="submit" class="btn btn-outline-primary" value="Back to Main Page">
    </form>
</div>
</body>
</html>
