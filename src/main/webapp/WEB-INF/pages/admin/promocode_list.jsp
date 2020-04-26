<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Alena">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Admin Page</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<style>
    .selected {
        background-color: rgba(0,0,0,.075);
    }
    .table_heading {
        background-color: #7a7e82;
        color: white
    }
</style>
<script>
    var selectedRow = null;
    var selectedItemId = null;

    function select_row(row, id) {
        if (selectedRow != null) {
            selectedRow.className = '';
        }
        selectedRow = row;
        selectedItemId = id;
        if (selectedRow != null) {
            selectedRow.className = 'selected';
        }
    }

    function delete_item() {
        if (selectedItemId != null && selectedRow != null) {
            var request = new XMLHttpRequest();
            request.responseType = "text";
            request.onreadystatechange = function () {
                if (this.status === 200 && this.responseText !== '' && selectedRow !== null) {
                    var i = selectedRow.rowIndex;
                    document.getElementById("mainTable").deleteRow(i);
                    selectedRow = null;
                    selectedItemId = null;
                    $('#successDeleteModal').modal('show');
                }
            };
            request.open("DELETE", "delete?id=" + selectedItemId, true);
            request.send();
        }
    }

    function logout() {
        var request = new XMLHttpRequest();
        request.responseType = "text";
        request.onreadystatechange = function () {
            window.location.replace("${pageContext.request.contextPath}/registration");
        };
        request.open("GET", "${pageContext.request.contextPath}/logout", true);
        request.send();
    }
</script>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-3">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample08"
            aria-controls="navbarsExample08" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-md-left">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/jewelry/list">Jewelries</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/order/list">Orders</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/promocode/list">Promocodes</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/material/list">Materials</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/shop/list">Shops</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/settings/list">Settings</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/emails/list">Email Messages</a>
            </li>
        </ul>
    </div>

    <button class="btn btn-light justify-content-md-right" type="button" onclick="logout()">Logout</button>
</nav>

<spring:form method="get" action="/promocode/list" modelAttribute="promocodeList">
    <div class="container-fluid">
        <input class="btn btn-primary mb-3" type="button" value="Создать" onclick="location.href='edit'"/>
        <input class="btn btn-primary mb-3" type="button" value="Удалить" onclick="delete_item()"/>
        <table class="table table-bordered" id="mainTable">
            <tr class="table_heading">
                <th>Id</th>
                <th>Active</th>
                <th>Code</th>
                <th>Type</th>
                <th>Value</th>
                <th>Max Uses Number</th>
                <th>Current Uses Number</th>
                <th>Expiration Date</th>
                <th>Max Jewelries</th>
            </tr>
            <c:forEach items="${promocodeList}" var="item" varStatus="status">
                <tr onclick="select_row(this, ${item.id})" ondblclick="location.href='edit?id=${item.id}'" style="cursor: pointer">
                    <td>${item.id}</td>
                    <td>${item.active}</td>
                    <td>${item.code}</td>
                    <td>${item.promoCodeType.name}</td>
                    <td>${item.value}</td>
                    <td>${item.maxUsesNumber}</td>
                    <td>${item.currentUsesNumber}</td>
                    <td>${item.expirationDate}</td>
                    <td>${item.maxJewelries}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="modal fade" id="successDeleteModal" role="dialog">
        <div class="modal-dialog">
            <div class="alert alert-success alert-dismissible">
                <a  class="close" data-dismiss="modal" aria-label="close">&times;</a>
                <strong>Deleting</strong> was successful
            </div>
        </div>
    </div>
    <br/>
</spring:form>

</body>
</html>