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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>

<style>
    .selected {
        background-color: rgba(0, 0, 0, .075);
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
                }
            };
            request.open("DELETE", "delete?id=" + selectedItemId, true);
            request.send();
        }
    }

    function sendEmail() {
        if (selectedItemId != null && selectedRow != null) {
            var request = new XMLHttpRequest();
            request.responseType = "text";
            request.onreadystatechange = function () {
                if (this.status === 200 && this.responseText !== '' && selectedRow !== null) {

                }
            };
            request.open("GET", "sendEmail?id=" + selectedItemId, true);
            request.send();
        }
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
</nav>

<spring:form method="get" action="/emails/list" modelAttribute="emailsList">
    <div class="container-fluid">
        <input class="btn btn-primary mb-3" type="button" value="Создать" onclick="location.href='edit'"/>
        <input class="btn btn-primary mb-3" type="button" value="Удалить" onclick="delete_item()"/>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal"
                style="margin-bottom: 16px"
                data-whatever="@mdo">Отправить</button>

        <table class="table table-bordered" id="mainTable">
            <tr class="table_heading">
                <th>Id</th>
                <th>To</th>
                <th>Message</th>
            </tr>
            <c:forEach items="${emailsList}" var="item" varStatus="status">
                <tr onclick="select_row(this, ${item.id})" ondblclick="location.href='edit?id=${item.id}'"
                    style="cursor: pointer">
                    <td>${item.id}</td>
                    <td>${item.type.name}</td>
                    <td>${item.message}</td>
                </tr>
            </c:forEach>
        </table>

        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Send Email</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Email:</label>
                                <input type="text" class="form-control" id="recipient-name">
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Order:</label>
                                <textarea class="form-control" id="message-text"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Send message</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br/>
</spring:form>

</body>
</html>