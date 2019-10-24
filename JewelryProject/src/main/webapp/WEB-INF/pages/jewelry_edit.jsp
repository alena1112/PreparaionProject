<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        .all-component-style {
            margin: 10px auto;/*величина отступа для всех сторон от родительского элемента*/
            max-width: 400px;
            padding: 20px 12px 10px 20px;/*отступы для содержимого компонента внутри него*/
            font: 13px "Lucida Sans Unicode", "Lucida Grande", sans-serif;
        }

        .all-component-style li {
            padding: 0;
            display: block;
            list-style: none;
            margin: 10px 0 0 0;
        }

        .all-component-style label {
            margin: 0 0 3px 0;
            padding: 0px;
            display: block;
            font-weight: bold;
        }

        .all-component-style input[type=text],
        .all-component-style input[type=date],
        .all-component-style input[type=datetime],
        .all-component-style input[type=number],
        .all-component-style input[type=search],
        .all-component-style input[type=time],
        .all-component-style input[type=url],
        .all-component-style input[type=email],
        textarea,
        select {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            border: 1px solid #BEBEBE;
            padding: 7px;
            margin: 0px;
            -webkit-transition: all 0.30s ease-in-out;
            -moz-transition: all 0.30s ease-in-out;
            -ms-transition: all 0.30s ease-in-out;
            -o-transition: all 0.30s ease-in-out;
            outline: none;
        }

        .all-component-style input[type=text]:focus,
        .all-component-style input[type=date]:focus,
        .all-component-style input[type=datetime]:focus,
        .all-component-style input[type=number]:focus,
        .all-component-style input[type=search]:focus,
        .all-component-style input[type=time]:focus,
        .all-component-style input[type=url]:focus,
        .all-component-style input[type=email]:focus,
        .all-component-style textarea:focus,
        .all-component-style select:focus {
            -moz-box-shadow: 0 0 8px #ddd;
            -webkit-box-shadow: 0 0 8px #ddd;
            box-shadow: 0 0 8px #ddd;
            border: 1px solid #ddd;
        }

        .all-component-style .field-divided {
            width: 49%;
        }

        .all-component-style .field-long {
            width: 100%;
        }

        .all-component-style .field-select {
            width: 100%;
        }

        .all-component-style .field-textarea {
            height: 100px;
        }

        .all-component-style button, .all-component-style input[type=button],
        .all-component-style input[type=submit] {
            background: black;
            padding: 8px 15px 8px 15px;
            border: none;
            color: #fff;
        }

        .all-component-style button:hover, .all-component-style input[type=button]:hover,
        .all-component-style input[type=submit]:hover {
            background: black;
            box-shadow: none;
            -moz-box-shadow: none;
            -webkit-box-shadow: none;
        }

        .all-component-style .required {
            color: black;
        }
    </style>
</head>
<body>

<h2>Administration page</h2>
<p>Редактирование украшения</p>

<spring:form method="post" action="/jewelry/save?id=${jewelryItem.id}" modelAttribute="jewelryItem">
    <ul class="all-component-style">
        <li>
            <label>Name <span class="required">*</span></label>
            <input type="text" name="name" path="name" class="field-long" value="${jewelryItem.name}"/>
        </li>
        <li>
            <label>Price <span class="required">*</span></label>
            <input type="text" name="price" path="price" class="field-long" value="${jewelryItem.price}"/>
        </li>
        <li>
            <label>Description</label>
            <textarea name="description" id="description" path="description"
                      class="field-long field-textarea">${jewelryItem.description}</textarea>
        </li>
        <li>
            <label>Type <span class="required">*</span></label>
            <spring:select path="type" class="field-long">
                <spring:options items="${jewelryTypes}" itemLabel="name"/>
            </spring:select>
        </li>
        <li>
            <label>Image URL <span class="required">*</span></label>
            <input type="text" name="imageUrl" path="imageUrl" class="field-long" value="${jewelryItem.imageUrl}"/>
        </li>
        <li>
            <input type="submit" value="Сохранить"/>
            <input type="button" value="Закрыть" onclick="location.href='list'"/>
        </li>
    </ul>
</spring:form>

</body>
</html>