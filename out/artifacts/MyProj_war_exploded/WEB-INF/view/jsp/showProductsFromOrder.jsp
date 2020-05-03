<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roma
  Date: 2020-04-27
  Time: 6:18 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../jsp/header.jsp" %>
<html>
<head>
    <title>ProductsFromOrder</title>
</head>
<body style="background-image: url('/view/images/background.jpg')">
<c:choose>
    <c:when test="${empty productMap}">
        <div class="text-center mt-5">
            <h1 class="text-white text-center">Your Cart is empty</h1>
        </div>
    </c:when>
    <c:when test="${not empty productMap}">
        <div class="container" id="container-cart">
            <div class="card-columns">
                <c:forEach var="mapProduct" items="${productMap}">
                    <div class="card bg-transparent" style="width:400px">
                        <img class="card-img mt-5" src="/view/images/productsImages/${mapProduct.key.imageName}"
                             alt="Card image">
                        <div class="card-body text-center">
                            <h4 class="card-title text-white">${mapProduct.key.name}</h4>
                            <p class="card-text text-white">
                                    ${mapProduct.key.type}
                                    ${mapProduct.key.capacity}
                                    ${mapProduct.key.color}
                                    ${mapProduct.key.price}${mapProduct.key.currency}<br>
                                    quantity = ${mapProduct.value}
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:when>
</c:choose>
</body>
</html>
