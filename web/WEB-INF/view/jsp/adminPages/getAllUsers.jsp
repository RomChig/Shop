<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roma
  DateHelper: 2020-04-09
  Time: 8:41 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<html>
<body style="background-image: url('/view/images/background.jpg')">
<div class="container">
    <div class="card-columns">
        <c:forEach var="customer" items="${users}">
            <c:if test="${customer.role.name=='customer'}">
                <div class="card bg-transparent text-center" style="width:250px">
                    <img class="card-img mt-5" src="/view/images/boy.png" alt="Card image" style="width:100%">
                    <div class="card-body text-center">
                        <h4 class="card-title text-white">${customer.firstName} ${customer.lastName}</h4>
                        <p class="card-text text-white">
                            Login: ${customer.login}<br>
                            Role: ${customer.role.name}
                        </p>
                        <div class="form form-check-inline">
                            <c:choose>
                                <c:when test="${customer.isBlocked eq false}">
                                    <form action="/BlockUser" method="post">
                                        <input class="btn btn-outline-danger" type="submit" value="Block">
                                        <input type="hidden" value="${customer.id}" name="id">
                                    </form>
                                </c:when>
                                <c:when test="${customer.isBlocked}">
                                    <form action="/UnBlockUser" method="post">
                                        <input class="btn btn-outline-success" type="submit" value="UnBlock">
                                        <input type="hidden" value="${customer.id}" name="id">
                                    </form>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>
</body>
</html>
