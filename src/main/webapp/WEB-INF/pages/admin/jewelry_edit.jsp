<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Alena">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Admin</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous">
    </script>

    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script>
        function uploadImage(input, i) {
            if (input.files && input.files[0]) {
                var formData = new FormData();
                formData.append("image", input.files[0]);
                formData.append("imageOrder", i);

                var request = new XMLHttpRequest();
                request.responseType = "text";
                request.onreadystatechange = function () {
                    if (this.status === 200 && this.responseText !== '') {
                        var path = this.responseText;
                        document.getElementById("image" + i).src = path;
                        document.getElementById("customFileLabel" + i).innerText =
                            path.substring(path.lastIndexOf('=') + 1, path.length);
                    }
                };
                request.open("POST", "uploadImage", true);
                request.send(formData);
            }
        }

        function deleteImage(i, defaultImg) {
            var request = new XMLHttpRequest();
            request.responseType = "text";

            request.onreadystatechange = function () {
                if (this.status === 200 && this.responseText === 'ok') {
                    document.getElementById("image" + i).src = defaultImg;
                    document.getElementById("customFileLabel" + i).innerText = "";
                }
            };
            request.open("DELETE", "deleteImage?position=" + i, true);
            request.send();
        }

        function loadMaterials() {
            var shopOption = document.getElementById("shopOption")
            var shopName = shopOption.options[shopOption.selectedIndex].id
            var orderDateOption = document.getElementById("orderDateOption")
            var orderId = orderDateOption.options[orderDateOption.selectedIndex].id;
            var name = document.getElementById("nameInput").value

            var request = new XMLHttpRequest();
            request.responseType = "text";

            request.onreadystatechange = function () {
                if (this.status === 200) {
                    var dataArray = JSON.parse(this.body);
                    var tableelement = document.getElementById("materialsTable")

                    var keys = Object.keys(dataArray), times = {}, rows = {};

                    (function processData() {

                        var row, key, r;

                        for (key in data) {
                            row = rows[key] = {};
                            for (r in dataArray[key]) addInfo(row, dataArray[key][r]);
                        }

                        function addInfo(row, record) {
                            times[record.Time] = true;
                            row[record.Time] = record.Count;
                        }

                    })();

                    (function createTable() {

                        var key,
                            count,
                            time,
                            tr = "<tr>",
                            $body = "body",
                            $table = "<table>",
                            $thead = "<thead>",
                            $tbody = "<tbody>";

                        $body.append($table);
                        $table.append($thead);
                        $table.append($tbody);

                        $thead.append(tr);

                        tr.append('<th>name</th>');

                        for (time in times) tr.append('<th>' + time + '</th>');

                        for (key in rows) {
                            tr = '<tr>';
                            tr.append('<th>' + key + '</th>');
                            for (time in times) {
                                count = (rows[key][time] || 0);
                                tr.append('<td>' + count + '</td>');
                            }
                            $tbody.append(tr);
                        }

                    })();
                }
            };
            request.open("GET", "loadMaterials?shop=" + shopName + "&orderId=" + orderId + "&name=" + name, true);
            request.send();
        }
    </script>

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        figure {
            width: 100%;
            height: 150px;
            margin: 0 0 3px;
        }

        figure img {
            width: 100%;
            height: 100%;
            object-fit: scale-down;
        }

        .input-group p {
            color: #7a7e82;
            font-size: 12px;
        }

        .img-div {
            width: 150px;
        }

        .file-label {
            white-space: nowrap;
            overflow: hidden;
            padding: 5px;
        }
    </style>
</head>

<body class="bg-light">
<spring:form method="post" action="${pageContext.request.contextPath}/admin/jewelry/save"
             modelAttribute="jewelry">
    <div class="container">
        <div class="py-2 text-center">
            <h3>${jewelry.name != null ? jewelry.name : "Jewelry"} Edit</h3>
            <p class="lead"></p>
        </div>

        <div>
            <form class="needs-validation" novalidate>
                <div class="row">
                    <c:forEach var="i" begin="0" end="4" step="1" varStatus="status">
                        <div class="col-md-2 img-div">
                            <div class="mb-2">
                                <figure>
                                    <img src="${imageHelper.getImageFullPathOrDefault(i)}"
                                         alt="img"
                                         id="image${i}">
                                </figure>
                                <div class="input-group">
                                    <div class="custom-file">
                                        <input type="file" class="custom-file-input" id="customFile${i}" name="filename"
                                               onchange="uploadImage(this, ${i})" multiple accept="image/*,image/jpeg">
                                        <label class="custom-file-label file-label" id="customFileLabel${i}"
                                               for="customFile${i}">${jewelry.getImage(i) != null ? jewelry.getImage(i).name : ""}</label>
                                    </div>
                                    <button class="btn btn-outline-secondary" type="button"
                                            onclick="deleteImage(${i}, '${imageHelper.getDefaultImage()}')">X
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="mb-3">
                    <label for="rating">Rating</label>
                    <spring:select class="custom-select d-block w-100" id="rating" path="rating" required="required">
                        <option value="">Choose...</option>
                        <spring:option value="1"/>
                        <spring:option value="2"/>
                        <spring:option value="3"/>
                        <spring:option value="4"/>
                        <spring:option value="5"/>
                    </spring:select>
                    <div class="invalid-feedback">
                        Please select a valid rating.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="name">Name</label>
                    <spring:input type="text" class="form-control" id="name" placeholder="" path="name"
                                  value="${jewelry.name}" required="required"/>
                    <div class="invalid-feedback">
                        Valid name is required.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="originalPrice">Original Price</label>
                    <spring:input type="text" class="form-control" id="originalPrice" path="originalPrice"
                                  value="${jewelry.originalPrice}"/>
                    <div class="invalid-feedback" style="width: 100%;">
                        Your Original Price is required.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="price">Price</label>
                    <spring:input type="text" class="form-control" id="price" path="price" value="${jewelry.price}"
                                  required="required"/>
                    <div class="invalid-feedback" style="width: 100%;">
                        Your price is required.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="description">Description <span class="text-muted">(Optional)</span></label>
                    <spring:textarea class="form-control" id="description" rows="3"
                                     path="description"/>
                    <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="type">Type</label>
                    <spring:select class="custom-select d-block w-100" id="type" path="type" required="required">
                        <option value="">Choose...</option>
                        <spring:options items="${jewelryTypes}" itemLabel="name"/>
                    </spring:select>
                    <div class="invalid-feedback">
                        Please select a valid type.
                    </div>
                </div>

                <hr class="mb-4">
                <div class="custom-control custom-checkbox">
                    <spring:checkbox class="custom-control-input" id="isHide" path="isHide"
                                     value="${jewelry.isHide}"/>
                    <label class="custom-control-label" for="isHide">Hide</label>
                </div>
                <div class="custom-control custom-checkbox">
                    <spring:checkbox class="custom-control-input" id="isSold" path="isSold"
                                     value="${jewelry.isSold}"/>
                    <label class="custom-control-label" for="isSold">Sold</label>
                </div>
                <hr class="mb-4">

                <div class="mb-3">
                    <label for="materialDescription">Material Description <span
                            class="text-muted">(Optional)</span></label>
                    <spring:textarea class="form-control" id="materialDescription" rows="3"
                                     path="materialDescription"/>
                    <div class="invalid-feedback">
                        Please enter a valid Material Description.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="weight">Weight <span class="text-muted">(Optional)</span></label>
                    <br>
                    <label class="text-muted">гр.</label>
                    <br>
                    <label class="text-muted">Вес одной сережки гр.</label>
                    <spring:input type="text" class="form-control" id="weight" path="weight" value="${jewelry.weight}"/>
                </div>

                <div class="mb-3">
                    <label for="size">Size <span class="text-muted">(Optional)</span></label>
                    <br>
                    <label class="text-muted">Длина браслета см.</label>
                    <br>
                    <label class="text-muted">Длина колье см.</label>
                    <br>
                    <label class="text-muted">Длина сережки см.</label>
                    <br>
                    <label class="text-muted">Длина регулируемой части см.</label>
                    <spring:input type="text" class="form-control" id="size" path="size" value="${jewelry.size}"/>
                </div>

                <div class="mb-3">
                    <label for="jewelryMaterialsTable">Materials <span class="text-muted">(Optional)</span></label>
                    <table class="table table-bordered" id="jewelryMaterialsTable">
                        <tr class="table_heading">
                            <th>Image</th>
                            <th>Name</th>
                            <th>Unit Price With Delivery</th>
                            <th>Number</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach items="${jewelryMaterials}" var="item" varStatus="status">
                            <tr>
                                <td>
                                    <img src="${item.material.imageURL}"
                                         alt="material"
                                         style="width:50px; height:50px">
                                </td>
                                <td>${item.material.name}</td>
                                <td>${item.material.unitPriceWithDelivery}</td>
                                <td>${item.number}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button class="btn btn-secondary" type="button" data-toggle="modal"
                            data-target="#addMaterialModal"
                            data-whatever="@mdo">Добавить
                    </button>
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary" type="submit">Сохранить</button>
                <button class="btn btn-primary" type="button" onclick="location.href='list'">Закрыть</button>
            </form>
        </div>
    </div>

    <div class="modal fade" id="addMaterialModal" tabindex="-1" role="dialog" aria-labelledby="addMaterialModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addMaterialModalLabel">Add Material</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <div class="row">

                                <div style="margin:0 3px 0 3px;">
                                    <label for="shopOption">Shop</label>
                                    <select class="custom-select d-block w-100" id="shopOption">
                                        <option value="">Choose...</option>
                                        <c:forEach items="${shops}" var="shop">
                                            <option id="${shop}" value="${shop}">${shop}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div style="margin-right: 3px">
                                    <label for="orderDateOption">Order Date</label>
                                    <select class="custom-select d-block w-100" id="orderDateOption">
                                        <option value="">Choose...</option>
                                        <c:forEach items="${orders}" var="order">
                                            <option id="${order}" value="${order}">${order.purchaseDate}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div style="margin-right: 3px">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="nameInput"/>
                                </div>

                                <button style="margin: 10px" type="button" class="btn btn-secondary"
                                        onclick="loadMaterials()">Search
                                </button>

                            </div>
                            <table class="table table-bordered" id="materialsTable">
                                <tr class="table_heading">
                                    <th>Image</th>
                                    <th>Name</th>
                                    <th>Unit Price With Delivery</th>
                                    <th>Number</th>
                                    <th>Add</th>
                                </tr>
                                    <%--                                <c:forEach items="${materials}" var="item" varStatus="status">--%>
                                    <%--                                    <tr>--%>
                                    <%--                                        <td>--%>
                                    <%--                                            <img src="${item.material.imageURL}"--%>
                                    <%--                                                 alt="material"--%>
                                    <%--                                                 style="width:50px; height:50px">--%>
                                    <%--                                        </td>--%>
                                    <%--                                        <td>${item.material.name}</td>--%>
                                    <%--                                        <td>${item.material.unitPriceWithDelivery}</td>--%>
                                    <%--                                        <td>${item.number}</td>--%>
                                    <%--                                    </tr>--%>
                                    <%--                                </c:forEach>--%>
                            </table>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="">Add</button>
                </div>
            </div>
        </div>
    </div>

</spring:form>
</body>