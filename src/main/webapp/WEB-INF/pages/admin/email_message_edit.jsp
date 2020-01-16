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
</head>

<body class="bg-light">
<spring:form method="post" action="${pageContext.request.contextPath}/admin/emails/save?id=${emailMsg.id}"
             modelAttribute="emailMsg">
    <div class="container">
        <div class="py-2 text-center">
            <h3>Email Message Edit</h3>
            <p class="lead"></p>
        </div>

        <div>
            <form class="needs-validation" novalidate>
                <div class="mb-3">
                    <label for="type">To</label>
                    <spring:select class="custom-select d-block w-100" id="type" path="type"
                                   required="required">
                        <option value="">Choose...</option>
                        <spring:options items="${toTypes}" itemLabel="name"/>
                    </spring:select>
                    <div class="invalid-feedback">
                        Please select a valid type.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="description">Message</label>
                    <spring:textarea class="form-control" id="description" rows="5"
                                     path="message"/>
                    <div class="invalid-feedback">
                        Please enter a valid message.
                    </div>
                </div>

                <hr class="mb-4">
                <button class="btn btn-primary" type="submit">Сохранить</button>
                <button class="btn btn-primary" type="button" onclick="location.href='list'">Закрыть</button>
            </form>
        </div>
    </div>
</spring:form>
</body>