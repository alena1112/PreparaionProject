<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <style>

    </style>
</head>
<body>

<h2>Administration page</h2>
<p>Редактирование украшения</p>

<spring:form method="get" action="/jewelry/list" modelAttribute="jewelryList">
    <div style="overflow-x:auto;">

    </div>
    <br/>
</spring:form>

</body>
</html>