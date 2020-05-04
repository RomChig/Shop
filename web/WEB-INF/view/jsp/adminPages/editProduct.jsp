<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roma
  DateHelper: 2020-04-07
  Time: 12:59 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<html>
<c:set var="setSelected" value="selected" scope="page"/>
<link rel="stylesheet" href="/view/style/adminPages/editProduct.css">
<body style="background-image: url('/view/images/background.jpg')">
<div class="coverForm">
    <div class="form">
        <form action="/Edit" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label class="text-white" for="name">Name:</label><br>
                <input class="form-control" type="text" name="name" id=name value="${product.name}" required pattern=^[A-Za-z\s]{3,20}$><br>
            </div>
            <div class="form-group">
                <label class="text-white" for="type">Type:</label><br>
                <input class="form-control" type="text" name="type" id="type" value="${product.type}" required pattern=^[A-Za-z\s]{3,20}$><br>
            </div>
            <div class="form-group">
                <label class="text-white" for="price">Price</label><br>
                <input class="form-control" type="text" name="price" id="price" value="${product.price}" required
                       pattern=\d+\.\d{2}><br>
            </div>
            <div id="centerForm">
                <div class="form-group">
                    <label class="text-white" for="capacity">Capacity:</label>
                    <select id="capacity" name="capacity" class="btn btn-outline-info btn-sm bg-light bg-dark">
                        <c:choose>
                            <c:when test="${product.capacity == '16Gb'}">
                                <option value="16Gb">16Gb</option>
                                <option value="32Gb">32Gb</option>
                                <option value="64Gb">64Gb</option>
                                <option value="128Gb">128Gb</option>
                                <option value="256Gb">256Gb</option>
                                <option value="512Gb">512Gb</option>
                            </c:when>
                            <c:when test="${product.capacity == '32Gb'}">
                                <option value="16Gb">16Gb</option>
                                <option${setSelected} value="32Gb">32Gb</option>
                                <option value="64Gb">64Gb</option>
                                <option value="128Gb">128Gb</option>
                                <option value="256Gb">256Gb</option>
                                <option value="512Gb">512Gb</option>
                            </c:when>
                            <c:when test="${product.capacity == '64Gb'}">
                                <option value="16Gb">16Gb</option>
                                <option value="32Gb">32Gb</option>
                                <option ${setSelected} value="64Gb">64Gb</option>
                                <option value="128Gb">128Gb</option>
                                <option value="256Gb">256Gb</option>
                                <option value="512Gb">512Gb</option>
                            </c:when>
                            <c:when test="${product.capacity == '128Gb'}">
                                <option value="16Gb">16Gb</option>
                                <option value="32Gb">32Gb</option>
                                <option value="64Gb">64Gb</option>
                                <option ${setSelected} value="128Gb">128Gb</option>
                                <option value="256Gb">256Gb</option>
                                <option value="512Gb">512Gb</option>
                            </c:when>
                            <c:when test="${product.capacity == '256Gb'}">
                                <option value="16Gb">16Gb</option>
                                <option value="32Gb">32Gb</option>
                                <option value="64Gb">64Gb</option>
                                <option value="128Gb">128Gb</option>
                                <option ${setSelected} value="256Gb">256Gb</option>
                                <option value="512Gb">512Gb</option>
                            </c:when>
                            <c:when test="${product.capacity == '512Gb'}">
                                <option value="16Gb">16Gb</option>
                                <option value="32Gb">32Gb</option>
                                <option value="64Gb">64Gb</option>
                                <option value="128Gb">128Gb</option>
                                <option value="256Gb">256Gb</option>
                                <option ${setSelected} value="512Gb">512Gb</option>
                            </c:when>
                        </c:choose>
                    </select><br>
                </div>
                <div class="form-group">
                    <label class="text-white" for="color">Color:</label>
                    <select id="color" name="color" class="btn btn-outline-info btn-sm bg-light bg-dark">
                        <c:choose>
                            <c:when test="${product.color == 'black'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'white'}">
                                <option value="black">black</option>
                                <option ${setSelected} value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'yellow'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option ${setSelected} value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'purple'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option ${setSelected} value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'red'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option ${setSelected} value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'gold'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option ${setSelected} value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'green'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option ${setSelected} value="green" style="color: green">green</option>
                                <option value="blue" style="color: blue">blue</option>
                            </c:when>
                            <c:when test="${product.color == 'blue'}">
                                <option value="black">black</option>
                                <option value="white" style="color: white"> white</option>
                                <option value="yellow" style="color: yellow">yellow</option>
                                <option value="purple" style="color: purple"> purple</option>
                                <option value="red" style="color: red">red</option>
                                <option value="gold" style="color: gold">gold</option>
                                <option value="green" style="color: green">green</option>
                                <option ${setSelected} value="blue" style="color: blue">blue</option>
                            </c:when>
                        </c:choose>
                    </select>
                </div>
                <div class="form-group">
                    <label class="text-white" for="currency">Currency:</label>
                    <select id="currency" name="currency" class="btn btn-outline-info btn-sm bg-light bg-dark">
                        <c:choose>
                            <c:when test="${product.currency == '$'}">
                                <option value="$">$</option>
                                <option value="€">€</option>
                                <option value="₴">₴</option>
                            </c:when>
                            <c:when test="${product.currency == '€'}">
                                <option value="$">$</option>
                                <option ${setSelected} value="€">€</option>
                                <option value="₴">₴</option>
                            </c:when>
                            <c:when test="${product.currency == '₴'}">
                                <option value="$">$</option>
                                <option value="€">€</option>
                                <option ${setSelected} value="₴">₴</option>
                            </c:when>
                        </c:choose>
                    </select><br>
                </div>
                <div class="form-group">
                    <input class="btn btn-outline-primary" type="file" name="imgPhone" accept="image/png" required><br>
                </div>
                <input type="hidden" value="${product.id}" name="product_id">
                <input type="submit" class="btn btn-outline-success" value="Edit">
            </div>
        </form>
    </div>
</div>
</body>
</html>
