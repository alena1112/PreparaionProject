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
    </script>

    <style>
    </style>
</head>

<body class="bg-light">
<spring:form method="post" action="${pageContext.request.contextPath}/admin/promocode/save?id=${promocode.id}" modelAttribute="promocode">
    <div class="container">
        <div class="py-2 text-center">
            <h3>Promocode Edit</h3>
            <p class="lead"></p>
        </div>

        <div>
            <form class="needs-validation" novalidate>
                <div class="mb-3">
                    <div class="mb-3">
                        <label for="name">Code</label>
                        <spring:input type="text" class="form-control" id="code" placeholder="" path="code"
                               value="${promocode.code}" required="required"/>
                        <div class="invalid-feedback">
                            Valid code is required.
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="value">Value</label>
                    <spring:input type="text" class="form-control" id="value" path="value" value="${promocode.value}"
                           required="required"/>
                    <div class="invalid-feedback" style="width: 100%;">
                        Your value is required.
                    </div>
                </div>

                <hr class="mb-4">
                <div class="custom-control custom-checkbox">
                    <spring:checkbox class="custom-control-input" id="isActive" path="active"
                           value="${promocode.active}"/>
                    <label class="custom-control-label" for="isActive">Active</label>
                </div>
                <hr class="mb-4">

                <div class="mb-3">
                    <label for="promoCodeType">Type</label>
                    <spring:select class="custom-select d-block w-100" id="promoCodeType" path="promoCodeType" required="required">
                        <option value="">Choose...</option>
                        <spring:options items="${promocodeTypes}" itemLabel="name"/>
                    </spring:select>
                    <div class="invalid-feedback">
                        Please select a valid type.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="maxUsesNumber">Max Uses Number</label>
                    <spring:input type="text" class="form-control" id="maxUsesNumber" path="maxUsesNumber"
                        value="${promocode.maxUsesNumber}"/>
                </div>

                <div class="mb-3">
                    <label for="currentUsesNumber">Current Uses Number</label>
                    <spring:input type="text" class="form-control" id="currentUsesNumber" path="currentUsesNumber"
                        value="${promocode.currentUsesNumber}"/>
                </div>

                <!--
                <div class="mb-3">
                    <label for="expirationDate">Expiration Date</label>
                    <spring:input type="text" class="form-control" id="expirationDate" path="expirationDate"
                        value="${promocode.expirationDate}"/>
                </div>
                -->

                <div class="mb-3">
                    <label for="maxJewelries">Max Jewelries</label>
                    <spring:input type="text" class="form-control" id="maxJewelries" path="maxJewelries"
                        value="${promocode.maxJewelries}"/>
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary" type="submit">Сохранить</button>
                <button class="btn btn-primary" type="button" onclick="location.href='list'">Закрыть</button>
            </form>
        </div>
    </div>
</spring:form>
</body>