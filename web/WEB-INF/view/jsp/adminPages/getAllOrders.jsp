<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roma
  DateHelper: 2020-04-13
  Time: 11:56 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<html>
<link rel="stylesheet" href="/view/style/adminPages/getAllOrders.css">
<body style="background-image: url('/view/images/background.jpg')">
<div class="container mt-5" id="container">
    <table class="table table-dark table-hover table-bordered text-center">
        <thead class="thead-light">
        <tr>
            <th>User</th>
            <th>Date</th>
            <th>Bill</th>
            <th>Status</th>
            <th>Set Status</th>
            <th>See Products</th>
        </tr>
        </thead>
        <c:forEach var="order" items="${orderList}">
            <tbody>
            <tr>
                <td>${order.user.firstName} ${order.user.lastName}
                <td>${order.dateCreated}
                <td>${order.bill} $
                <td>${order.status.name}
                <td>
                    <div class="form-check">
                        <form action="/SetStatusForOrder" method="post">
                            <label for="setStatus"></label>
                            <select class="bg-transparent btn btn-outline-light" name="setStatus" id="setStatus">
                                <option value="paid">Paid</option>
                                <option value="canceled">Canceled</option>
                            </select>
                            <input type="submit" class="btn btn-outline-light" value="Set">
                            <input type="hidden" value="${order.id}" name="orderId">
                        </form>
                    </div>
                </td>
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
        <form action="/SortOrders" method="post">
            <label for="dateFrom" class="text-white">From date</label>
            <input class="form-control" type="date" name="dateFrom" id="dateFrom" required>
            <label for="dateTo" class="text-white">To date</label>
            <input class="form-control" type="date" name="dateTo" id="dateTo" required>
            <input type="submit" class="btn btn-success" value="Send">
        </form>
</div>
</body>
</html>
