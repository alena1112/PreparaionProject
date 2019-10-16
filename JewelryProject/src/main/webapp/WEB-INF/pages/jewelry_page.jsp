<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        table, td, th {
            border: 1px solid #ddd;
            text-align: left;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 15px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<h2>Administration page</h2>
<p>Список всех украшений</p>

<spring:form method="get" action="/jewelry/list" modelAttribute="jewelryList">
    <div style="overflow-x:auto;">
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Image URL</th>
            </tr>
            <c:forEach items="${jewelryList}" var="item" varStatus="status">
                <tr>
                    <td><a href="item?id=${item.id}">${item.id}</a></td>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.description}</td>
                    <td>${item.imageUrl}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br/>
</spring:form>

</body>
</html>