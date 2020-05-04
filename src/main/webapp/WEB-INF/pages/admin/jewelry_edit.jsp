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
                        Please enter a valid email address for shipping updates.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="weight">Weight <span class="text-muted">(Optional)</span></label>
                    <spring:textarea class="form-control" id="weight" rows="2" path="weight"/>
                </div>

                <div class="mb-3">
                    <label for="size">Size <span class="text-muted">(Optional)</span></label>
                    <spring:input type="text" class="form-control" id="size" path="size" value="${jewelry.size}"/>
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary" type="submit">Сохранить</button>
                <button class="btn btn-primary" type="button" onclick="location.href='list'">Закрыть</button>
            </form>
        </div>
    </div>
</spring:form>
</body>