<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        table, td, th {
            border: 1px solid #ddd;
            text-align: left;
        }

        table {
            margin: 10px auto;
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 15px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        input[type=button] {
            background: black;
            padding: 8px 15px 8px 15px;
            border: none;
            color: #fff;
        }
    </style>
</head>
<body>

<h2>Administration page</h2>
<p>Список всех украшений</p>

<spring:form method="get" action="/jewelry/list" modelAttribute="jewelryList">
    <input type="button" value="Создать" onclick="location.href='edit'"/>
    <div style="overflow-x:auto;">
        <table>
            <tr>
                <th></th>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Type</th>
                <th>Image URL</th>
            </tr>
            <c:forEach items="${jewelryList}" var="item" varStatus="status">
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}/resources/w3images/delete_button.png" style="width:100%"
                            onclick="location.href='delete?id=${item.id}'"/>
                    </td>
                    <td><a href="edit?id=${item.id}">${item.id}</a></td>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.description}</td>
                    <td>${item.type.name}</td>
                    <td>${item.imageUrl}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br/>
</spring:form>

</body>
</html>