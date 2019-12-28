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
        window.onload = function () {
            document.querySelectorAll('.custom-file-input').forEach(function (item, idx) {
                item.addEventListener('change', function (e) {
                    var fileName = document.getElementById("customFile").files[0].name;
                    var nextSibling = e.target.nextElementSibling;
                    nextSibling.innerText = fileName
                })
            });
        }

        function readURL(input, i) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    if (i === -1) {
                        document.getElementById("mainImage").src = e.target.result;
                    } else {
                        document.getElementById("image" + i).src = e.target.result;
                    }
                };

                reader.readAsDataURL(input.files[0]);
            }
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

        .custom-file p {
            color: #7a7e82;
            font-size: 12px;
        }

        .img-div {
            width: 150px;
        }
    </style>
</head>

<body class="bg-light">
<spring:form method="post" action="/jewelry/save?id=${jewelryItem.id}" modelAttribute="jewelryItem">
    <div class="container">
        <div class="py-2 text-center">
            <h3>${jewelryItem.name != null ? jewelryItem.name : "Jewelry"} Edit</h3>
            <p class="lead"></p>
        </div>

        <div>
            <form class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-md-2 img-div">
                        <div class="mb-2">
                            <figure>
                                <img src="${jewelryItem.mainImage.path}" alt="img" id="mainImage">
                            </figure>
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="customFile" name="filename"
                                       onchange="readURL(this, -1)" multiple accept="image/*,image/jpeg">
                                <label class="custom-file-label" for="customFile">${jewelryItem.mainImage.name}</label>
                                <p>Note: main image should be square</p>
                            </div>
                        </div>
                    </div>
                    <c:forEach var="i" begin="0" end="3" step="1" varStatus="status">
                        <div class="col-md-2 img-div">
                            <div class="mb-2">
                                <figure>
                                    <img src="${i < jewelryItem.images.size() ? jewelryItem.images.get(i).path :
                                "http://localhost:9999/resources/w3images/icons-plus.png"}"
                                         alt="img"
                                         id="image${i}">
                                </figure>
                                <div class="custom-file">
                                    <input type="file" class="custom-file-input" id="customFile" name="filename"
                                           onchange="readURL(this, ${i})" multiple accept="image/*,image/jpeg">
                                    <label class="custom-file-label"
                                           for="customFile">${i < jewelryItem.images.size() ? jewelryItem.images.get(i).name : ""}</label>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="mb-3">
                    <div class="mb-3">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" id="name" placeholder="" path="name"
                               value="${jewelryItem.name}" required>
                        <div class="invalid-feedback">
                            Valid name is required.
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="price">Price</label>
                    <input type="text" class="form-control" id="price" path="price" value="${jewelryItem.price}"
                           required>
                    <div class="invalid-feedback" style="width: 100%;">
                        Your price is required.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="description">Description <span class="text-muted">(Optional)</span></label>
                    <textarea class="form-control" id="description" rows="3" path="description"
                              required>${jewelryItem.description}</textarea>
                    <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="type">Type</label>
                    <spring:select class="custom-select d-block w-100" id="type" path="type">
                        <option value="">Choose...</option>
                        <spring:options items="${jewelryTypes}" itemLabel="name"/>
                    </spring:select>
                    <div class="invalid-feedback">
                        Please select a valid type.
                    </div>
                </div>

                <hr class="mb-4">
                <div class="custom-control custom-checkbox">
                    <input type="checkbox" class="custom-control-input" id="isHide" path="hide"
                           value="${jewelryItem.hide}">
                    <label class="custom-control-label" for="isHide">Hide</label>
                </div>
                <div class="custom-control custom-checkbox">
                    <input type="checkbox" class="custom-control-input" id="isSold" path="sold"
                           value="${jewelryItem.sold}">
                    <label class="custom-control-label" for="isSold">Sold</label>
                </div>
                <hr class="mb-4">

                <div class="mb-3">
                    <label for="materialDescription">Material Description <span
                            class="text-muted">(Optional)</span></label>
                    <textarea class="form-control" id="materialDescription" rows="3"
                              path="materialDescription" required>${jewelryItem.materialDescription}</textarea>
                    <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="weight">Weight <span class="text-muted">(Optional)</span></label>
                    <textarea class="form-control" id="weight" rows="2" path="weight">${jewelryItem.weight}</textarea>
                </div>

                <div class="mb-3">
                    <label for="size">Size <span class="text-muted">(Optional)</span></label>
                    <input type="text" class="form-control" id="size" path="size" value="${jewelryItem.size}">
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary" type="submit">Сохранить</button>
                <button class="btn btn-primary" type="button" onclick="location.href='list'">Закрыть</button>
            </form>
        </div>
    </div>
</spring:form>
</body>