<%--
  Created by IntelliJ IDEA.
  User: roma
  Date: 2020-04-09
  Time: 11:56 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="/WEB-INF/view/head.jspf" %>
    <title>UnbrokenPhone</title>
</head>
<body>
<nav class="navbar navbar-expand-md bg-transparent navbar-dark fixed-top">
    <a class="btn text-white fab fa-apple navbar-brand" href="/GetAllProducts"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar"
            aria-expanded="false">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <div class="dropdown">
                    <c:choose>
                        <c:when test="${empty user.login}">
                            <a class="nav-link text-white" href="/ProductCart">MyCart</a>
                        </c:when>
                        <c:when test="${not empty user.login}">
                            <a class="nav-link text-white dropdown-toggle" data-toggle="dropdown">My cabinet</a>
                            <div class="dropdown-menu">
                                <с:if test="${user.role.id == 1}">
                                    <a class="dropdown-item" data-toggle="modal"
                                       data-target="#addProduct">AddProduct</a>
                                    <a class="dropdown-item" href="/GetAllUsers">AllUsers</a>
                                    <a class="dropdown-item" href="/GetAllOrders">AllOrders</a>
                                </с:if>
                                <c:if test="${user.role.id == 2}">
                                    <a class="dropdown-item" href="/UserRoom">MyRoom</a>
                                    <a class="dropdown-item" href="/ProductCart">MyCart</a>
                                </c:if>
                            </div>
                        </c:when>
                    </c:choose>
                </div>
            </li>
            <c:if test="${empty user.login}">
                <li class="nav-item">
                    <a class="nav-link text-white" data-toggle="modal" data-target="#myModal">Log in</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" data-toggle="modal" data-target="#myModal2">Register</a>
                </li>
            </c:if>
        </ul>
        <c:if test="${not empty user.login}">
            <a class="nav-link text-white ml-auto" href="/Logout">Logout</a>
        </c:if>
    </div>
</nav>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content bg-dark">
            <div class="modal-header">
                <h4 class="modal-title text-white" id="exampleModalLabel">Log in</h4>
                <button class="close text-white" data-dismiss="modal">x</button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form action="/Authorization" method="post">
                        <div class="form-group">
                            <label class="text-white" for="login">Login:</label>
                            <input type="text" class="form-control" name="login" id="login"
                                   placeholder="Login" required><br>
                            <label class="text-white" for="password">Password:</label>
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="Password" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Send</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="exampleModal2" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content bg-dark">
            <div class="modal-header">
                <h4 class="modal-title text-white" id="exampleModalLabel2">Register</h4>
                <button class="close text-white" data-dismiss="modal">x</button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form action="/Registration" method="post">
                        <div class="form-group">
                            <label for="first_name" class="text-white">First Name:</label>
                            <input type="text" name="first_name" class="form-control" id="first_name"
                                   placeholder="First Name" required pattern="\S+"><br>
                            <label for="last_name" class="text-white">Last Name:</label>
                            <input type="text" name="last_name" id="last_name" class="form-control"
                                   placeholder="Last Name" required pattern="\S+"><br>
                            <label for="login2" class="text-white">Login:</label>
                            <input type="text" name="login" id="login2" class="form-control" placeholder="Login"
                                   required pattern="\S+"><br>
                            <label for="password2" class="text-white">Password:</label>
                            <input type="password" name="password" id="password2" class="form-control"
                                   placeholder="Password" required pattern="\S+">
                        </div>
                        <button type="submit" class="btn btn-primary">Send</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addProduct" tabindex="-1" role="dialog" aria-labelledby="exampleAddProduct"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content bg-dark">
            <div class="modal-header">
                <h4 class="modal-title text-white" id="exampleAddProductLabel">Add Product</h4>
                <button class="close text-white" data-dismiss="modal">x</button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form action="/AddProduct" method="post" enctype="multipart/form-data">
                        <label for="name" class="text-white">Name:</label><br>
                        <input class="form-control" type="text" name="name" id=name placeholder="Name" required><br>

                        <label for="type" class="text-white">Type:</label><br>
                        <input class="form-control" type="text" name="type" placeholder="Type" id="type" required><br>

                        <label for="price" class="text-white">Price</label><br>
                        <input class="form-control" type="text" name="price" placeholder="Price" id="price" required
                               pattern=\d+\.\d{2}><br>

                        <label for="capacity" class="text-white">Capacity:</label>
                        <select id="capacity" name="capacity" class="btn btn-outline-info btn-sm bg-light bg-dark">
                            <option value="16Gb">16Gb</option>
                            <option value="32Gb">32Gb</option>
                            <option value="64Gb">64Gb</option>
                            <option value="128Gb">128Gb</option>
                            <option value="256Gb">256Gb</option>
                            <option value="512Gb">512Gb</option>
                        </select>

                        <label for="color" class="text-white">Color:</label>
                        <select id="color" name="color" class="btn btn-outline-info btn-sm bg-light bg-dark">
                            <option value="black">black</option>
                            <option value="white" style="color: white"> white</option>
                            <option value="white" style="color: yellow">yellow</option>
                            <option value="white" style="color: purple"> purple</option>
                            <option value="red" style="color: red">red</option>
                            <option value="red" style="color: gold">gold</option>
                            <option value="red" style="color: green">green</option>
                            <option value="blue" style="color: blue">blue</option>
                        </select>

                        <label for="currency" class="text-white">Currency:</label>
                        <select id="currency" name="currency" class="btn btn-outline-info btn-sm bg-light bg-dark">
                            <option value="$">$</option>
                            <option value="€">€</option>
                            <option value="₴">₴</option>
                        </select><br>
                        <input type="file" name="imgPhone"><br>
                        <input type="submit" class="btn btn-primary" value="Add">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
</body>
</html>
