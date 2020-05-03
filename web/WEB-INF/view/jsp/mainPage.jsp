<%--
  Created by IntelliJ IDEA.
  User: roma
  DateHelper: 2020-03-23
  Time: 7:21 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title>UnbrokenPhone</title>
    <link rel="stylesheet" href="/view/style/mainPage.css" type="text/css">
</head>
<c:if test="${not empty sortByName}">
    <c:set var="ByName" value="selected" scope="page"/>
</c:if>
<c:if test="${not empty sortByPrice}">
    <c:set var="ByPrice" value="selected" scope="page"/>
</c:if>
<c:if test="${not empty sortByDateAdded}">
    <c:set var="ByDateAdded" value="selected" scope="page"/>
</c:if>
<c:if test="${not empty sortByCapacity}">
    <c:set var="ByCapacity" value="selected" scope="page"/>
</c:if>
<c:if test="${not empty sortByColor}">
    <c:set var="ByColor" value="selected" scope="page"/>
</c:if>
<body style="background-image: url('/view/images/background.jpg')">
<div class="container bg bg-transparent position-fixed" id="container-sort">
    <form action="/SortProducts" method="post" class="text-right">
        <label class="text-white">Name</label>
        <label>
            <select name="name" class="bg-transparent btn btn-outline-light" id="select1">
                <c:choose>
                    <c:when test="${sortByName =='Default' or empty sortByName}">
                        <option value="Default">Default</option>
                        <option value="a-z">A-Z</option>
                        <option value="z-a">Z-A</option>
                    </c:when>
                    <c:when test="${sortByName =='a-z'}">
                        <option value="Default">Default</option>
                        <option ${ByName} value="a-z">A-Z</option>
                        <option value="z-a">Z-A</option>
                    </c:when>
                    <c:when test="${sortByName == 'z-a'}">
                        <option value="Default">Default</option>
                        <option value="a-z">A-Z</option>
                        <option ${ByName} value="z-a">Z-A</option>
                    </c:when>
                </c:choose>
            </select>
        </label><br><br><br>
        <label class="text-white">Price</label>
        <label>
            <select name="price" class="bg-transparent btn btn-outline-light" id="select2">
                <c:choose>
                    <c:when test="${sortByPrice =='Default' or empty sortByPrice}">
                        <option value="Default">Default</option>
                        <option value="priceUp">Up</option>
                        <option value="priceDown">Down</option>
                    </c:when>
                    <c:when test="${sortByPrice =='priceUp'}">
                        <option value="Default">Default</option>
                        <option ${ByPrice} value="priceUp">Up</option>
                        <option value="priceDown">Down</option>
                    </c:when>
                    <c:when test="${sortByPrice == 'priceDown'}">
                        <option value="Default">Default</option>
                        <option value="priceUp">Up</option>
                        <option ${ByPrice} value="priceDown">Down</option>
                    </c:when>
                </c:choose>
            </select>
        </label><br><br><br>
        <label class="text-white">New</label>
        <label>
            <select name="date_added" class="bg-transparent btn btn-outline-light" id="select3">
                <c:choose>
                    <c:when test="${sortByDateAdded =='Default' or empty sortByDateAdded}">
                        <option value="Default">Default</option>
                        <option value="new">New</option>
                    </c:when>
                    <c:when test="${sortByDateAdded == 'new'}">
                        <option value="Default">Default</option>
                        <option ${ByDateAdded} value="new">New</option>
                    </c:when>
                </c:choose>
            </select>
        </label><br><br><br>
        <label class="text-white">Memory</label>
        <label>
            <select name="capacity" class="bg-transparent btn btn-outline-light" id="select4">
                <c:choose>
                    <c:when test="${sortByCapacity=='Default' or empty sortByCapacity}">
                        <option value="Default">Default</option>
                        <option value="64Gb">64Gb</option>
                        <option value="128Gb">128Gb</option>
                        <option value="256Gb">256Gb</option>
                        <option value="512Gb">512Gb</option>
                    </c:when>
                    <c:when test="${sortByCapacity == '64Gb'}">
                        <option value="Default">Default</option>
                        <option ${ByCapacity} value="64Gb">64Gb</option>
                        <option value="128Gb">128Gb</option>
                        <option value="256Gb">256Gb</option>
                        <option value="512Gb">512Gb</option>
                    </c:when>
                    <c:when test="${sortByCapacity == '128Gb'}">
                        <option value="Default">Default</option>
                        <option value="64Gb">64Gb</option>
                        <option ${ByCapacity} value="128Gb">128Gb</option>
                        <option value="256Gb">256Gb</option>
                        <option value="512Gb">512Gb</option>
                    </c:when>
                    <c:when test="${sortByCapacity == '256Gb'}">
                        <option value="Default">Default</option>
                        <option value="64Gb">64Gb</option>
                        <option value="128Gb">128Gb</option>
                        <option ${ByCapacity} value="256Gb">256Gb</option>
                        <option value="512Gb">512Gb</option>
                    </c:when>
                    <c:when test="${sortByCapacity == '512Gb'}">
                        <option value="Default">Default</option>
                        <option value="64Gb">64Gb</option>
                        <option value="128Gb">128Gb</option>
                        <option value="256Gb">256Gb</option>
                        <option ${ByCapacity} value="512Gb">512Gb</option>
                    </c:when>
                </c:choose>
            </select>
        </label><br><br><br>
        <label class="text-white">Color</label>
        <label>
            <select name="color" class="bg-transparent btn btn-outline-light" id="select5">
                <c:choose>
                    <c:when test="${sortByColor =='Default' or  empty sortByColor}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='green'}">
                        <option value="Default">Default</option>
                        <option ${ByColor} value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='black'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option ${ByColor} value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='yellow'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option ${ByColor} value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='red'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option ${ByColor} value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='purple'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option ${ByColor} value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='gold'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option ${ByColor} value="gold">gold</option>
                        <option value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='white'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option ${ByColor} value="white">white</option>
                        <option value="blue">blue</option>
                    </c:when>
                    <c:when test="${sortByColor =='blue'}">
                        <option value="Default">Default</option>
                        <option value="green">green</option>
                        <option value="black">black</option>
                        <option value="yellow">yellow</option>
                        <option value="red">red</option>
                        <option value="purple">purple</option>
                        <option value="gold">gold</option>
                        <option value="white">white</option>
                        <option ${ByColor} value="blue">blue</option>
                    </c:when>
                </c:choose>
            </select>
        </label><br><br>
        <button type="submit" class="btn btn-outline-light btn-lg" id="sortButton">Sort</button>
    </form>
</div>
<div class="container" id="container-cart">
    <div class="card-columns">
        <c:forEach var="product" items="${productList}">
            <div class="card bg-transparent" style="width:400px">
                <img class="card-img mt-5" src="/view/images/productsImages/${product.imageName}"
                     alt="Card image">
                <div class="card-body text-center">
                    <h4 class="card-title text-white">${product.name}</h4>
                    <p class="card-text text-white">
                            ${product.type}
                            ${product.capacity}
                            ${product.color}
                            ${product.price} ${product.currency}
                    </p>
                    <c:choose>
                        <c:when test="${user.role.name == 'admin'}">
                            <div class="form form-check-inline">
                                <form action="/PreEdit" method="post">
                                    <button type="submit" class="btn btn-outline-primary">Edit</button>
                                    <input type="hidden" value="${product.id}" name="product_id">
                                </form>
                                <form action="/DeleteProduct" method="post">
                                    <button type="submit" class="btn btn-outline-danger">Delete</button>
                                    <input type="hidden" value="${product.id}" name="product_id">
                                </form>
                            </div>
                        </c:when>
                        <c:when test="${user.role.name != 'admin'}">
                            <form action="/AddProductToCart" method="post">
                                <button type="submit" class="btn btn-outline-light">Add to cart</button>
                                <input type="hidden" value="${product.id}" name="product_id">
                            </form>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>