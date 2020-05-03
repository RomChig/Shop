<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<html>
    <body style="background-image: url('/view/images/background.jpg')">
<c:choose>
    <c:when test="${empty userCart.mapProduct}">
        <div class="text-center mt-5">
            <h1 class="text-white text-center">Your Cart is empty</h1>
        </div>
    </c:when>
    <c:when test="${not empty userCart.mapProduct}">
        <div class="text-center mt-5">
            <form action="/DeleteCart" method="post">
                <input type="submit" class="btn btn-lg btn-outline-danger" value="Delete Cart">
            </form>
        </div>
        <div class="container" id="container-cart">
            <div class="card-columns">
                <c:forEach var="mapa" items="${userCart.mapProduct}">
                    <div class="card bg-transparent" style="width:400px">
                        <img class="card-img mt-5" src="/view/images/productsImages/${mapa.key.imageName}" alt="Card image">
                        <div class="card-body text-center">
                            <h4 class="card-title text-white">${mapa.key.name}</h4>
                            <p class="card-text text-white">
                                    ${mapa.key.type}
                                    ${mapa.key.capacity}
                                    ${mapa.key.color}
                                    ${mapa.key.price}${mapa.key.currency}<br>
                                quantity = ${mapa.value}
                            <form action="/DeleteFromCart" method="post">
                                <input type="submit" class="btn btn-outline-danger" value="Delete">
                                <input type="hidden" value="${mapa.key.id}" name="product_id">
                                <input type="hidden" value="${mapa.value}" name="quantity">
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${not empty userCart.mapProduct and not empty user.login}">
                <div class="text-center">
                    <form action="/CreateOrder" method="post">
                        <button type="submit" class="btn btn-outline-success btn-lg">Create Order</button>
                        <input type="hidden" value="${userCart}" name="userCart">
                    </form>
                </div>
            </c:if>
        </div>
    </c:when>
</c:choose>
</body>
</html>