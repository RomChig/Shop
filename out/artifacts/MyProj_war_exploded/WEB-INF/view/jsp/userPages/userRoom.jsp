<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roma
  DateHelper: 2020-04-06
  Time: 5:44 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<html>
<head>
    <title>UnbrokenPhone</title>
    <%@include file="/WEB-INF/view/head.jspf" %>
</head>
<body style="background-image: url('/view/images/background.jpg')">
<c:choose>
    <c:when test="${empty orders}">
        <div class="text-center mt-5">
            <h1 class="text-white text-center">Your have no one order</h1>
        </div>
    </c:when>
    <c:when test="${ not empty orders}">
        <div class="container mt-5" id="container">
            <table class="table table-dark table-hover table-bordered text-center">
                <thead class="thead-light">
                <tr>
                    <th>Date</th>
                    <th>Bill</th>
                    <th>Status</th>
                    <th>See Products</th>
                </tr>
                </thead>
                <c:forEach var="order" items="${orders}">
                    <tbody>
                    <tr>
                        <td>${order.dateCreated}
                        <td>${order.bill} $
                        <td>${order.status.name}
                        <td>
                            <div class="form-check">
                                <form action="/OrdersProducts" method="post">
                                    <input type="submit" class="btn btn-outline-primary" value="Show">
                                    <input type="hidden" value="${order.id}" name="id">
                                </form>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </c:when>
</c:choose>
</body>
</html>
